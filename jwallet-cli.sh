#!/bin/bash

### build ###
function build-images() {
  mvn clean install -DskipTests
  docker-compose -f docker-compose.yaml build --no-cache
}
### compose ###
function run-compose() {
  docker-compose -f docker-compose.yaml up -d
}

function stop-compose() {
  docker-compose -f docker-compose.yaml down --remove-orphans
  sleep 5
  docker volume rm -f  jwallet_jwallet-data
}
### kubernetes ###
function run-cluster() {
  # cluster
  k3d registry create registry.localhost --port 12345
  k3d cluster create --registry-use k3d-registry.localhost:12345
  k3d cluster start
}

function stop-cluster() {
  k3d cluster stop
}

function deploy-manifests() {
  # postgres module
  # tag and push to kubernetes registry
  docker tag postgres:12.3 k3d-registry.localhost:12345/jwallet/postgres:latest
  docker push k3d-registry.localhost:12345/jwallet/postgres:latest
  # deploy module
  kubectl apply -f postgres/postgres-manifest.yaml
  sleep 5

  # keycloak module
  # tag and push to kubernetes registry
  docker tag quay.io/keycloak/keycloak:18.0.0 k3d-registry.localhost:12345/jwallet/keycloak:latest
  docker push k3d-registry.localhost:12345/jwallet/keycloak:latest
  # deploy module
  kubectl create cm keycloak-realm-cm --from-file=./keycloak/jwallet-realm.json
  kubectl apply -f keycloak/keycloak-manifest.yaml
  sleep 5

  # wallet module
  # tag and push to kubernetes registry
  docker tag jwallet/wallet:latest k3d-registry.localhost:12345/jwallet/wallet:latest
  docker push k3d-registry.localhost:12345/jwallet/wallet:latest
  # deploy module
  kubectl apply -f wallet/wallet-manifest.yaml
  sleep 5

  # rate module
  # tag and push to kubernetes registry
  docker tag jwallet/rate:latest k3d-registry.localhost:12345/jwallet/rate:latest
  docker push k3d-registry.localhost:12345/jwallet/rate:latest
  # deploy module
  kubectl apply -f rate/rate-manifest.yaml
  sleep 5

  # account module
  # tag and push to kubernetes registry
  docker tag jwallet/account:latest k3d-registry.localhost:12345/jwallet/account:latest
  docker push k3d-registry.localhost:12345/jwallet/account:latest
  # deploy module
  kubectl apply -f account/account-manifest.yaml
  sleep 5

  # jaeger
  # tag and push to kubernetes registry
  docker tag docker.io/jaegertracing/all-in-one:1.31 k3d-registry.localhost:12345/jaegertracing/all-in-one:1.31
  docker push k3d-registry.localhost:12345/jaegertracing/all-in-one:1.31
  # deploy module
  kubectl apply -f jaeger/jaeger-manifest.yaml
  sleep 5

  # prometheus
  # tag and push to kubernetes registry
  docker tag docker.io/prom/prometheus:v2.34.0 k3d-registry.localhost:12345/prom/prometheus:v2.34.0
  docker push k3d-registry.localhost:12345/prom/prometheus:v2.34.0
  # deploy module
  kubectl create cm prometheus-cm --from-file=./prometheus/prometheus.yaml
  kubectl apply -f prometheus/prometheus-manifest.yaml
  sleep 5

  # grafana
  # tag and push to kubernetes registry
  docker tag docker.io/grafana/grafana-oss:8.4.4 k3d-registry.localhost:12345/grafana/grafana-oss:8.4.4
  docker push k3d-registry.localhost:12345/grafana/grafana-oss:8.4.4
  # deploy module
  kubectl create cm grafana-cm --from-file=grafana/provisioning/datasources/ --from-file=grafana/provisioning/dashboards/
  kubectl apply -f grafana/grafana-manifest.yaml
  sleep 5
}

function delete-manifests() {
  # postgres module
  kubectl delete -f postgres/postgres-manifest.yaml

  # keycloak module
  kubectl delete cm/keycloak-realm-cm
  kubectl delete -f keycloak/keycloak-manifest.yaml

  # wallet module
  kubectl delete -f wallet/wallet-manifest.yaml

  # rate module
  kubectl delete -f rate/rate-manifest.yaml

  # account module
  kubectl delete -f account/account-manifest.yaml

  # jaeger
  kubectl delete -f jaeger/jaeger-manifest.yaml

  # prometheus
  kubectl delete cm/prometheus-cm
  kubectl delete -f prometheus/prometheus-manifest.yaml

  # grafana
  kubectl delete cm/grafana-cm
  kubectl delete -f grafana/grafana-manifest.yaml
}
### script ###

COMMAND="${1:-help}"

# Build docker images
if [ "$COMMAND" = "build" ]; then
  build-images
fi

# run jwallet on docker compose
if [ "$COMMAND" = "up-compose" ]; then
  run-compose
fi

#stop docker compose
if [ "$COMMAND" = "down-compose" ]; then
  stop-compose
fi

# run jwallet on kubernetes (using k3d)
if [ "$COMMAND" = "up-kube" ]; then
  run-cluster
  deploy-manifests
fi

# stop kubernetes
if [ "$COMMAND" = "down-kube" ]; then
  delete-manifests
  stop-cluster
fi

if [ "$COMMAND" = "deploy-kube" ]; then
  deploy-manifests
fi

if [ "$COMMAND" = "delete-kube" ]; then
  delete-manifests
fi

# help
if [ "$COMMAND" = "help" ]; then
  echo ""
  echo "Usage:"
  echo "  jwallet-cli.sh <command>"
  echo "Commands:"
  echo "  build       : build docker images"
  echo "  up-compose : run jwallet on docker-compose"
  echo "  down-compose: stop jwallet running on docker-compose"
  echo "  up-kube    : run jwallet on kubernetes (using k3d)"
  echo "  down-kube   : stop jwallet running on kubernetes (using k3d)"
  echo ""
fi

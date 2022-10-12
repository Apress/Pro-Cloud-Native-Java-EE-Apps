# JWallet

## Building & Testing

### Building

1. Maven wrapper is bundled
2. OpenLiberty maven plugin is configured, run a dev session using `./mvnw librety:dev`

### Integration Testing

1. Self-contained using `Testcontainers` using `tc` flag (e.g. `./mvnw verify -Dtc`)
2. Application is already running externally no flag required (e.g. `./mvnw verify`)

## Features

All features are pre-configured to run out of the box when running `JWallet` using the described methods bellow

### Database

`Postgresql` is bundled as the main database for jwallet

### Security

`Keycloak` is pre-configured with a realm with default user with credentials `max/max`

### Health

`Kubernetes` monitors and reacts to the application exposed health state (MP Heath)

### Distributed Tracing

`Jaeger` can be used to query traces exposed by the application (MP openTracing)

### Metrics

`Prometheus` scraps metrics exposed by the application (MP metrics)
`Grafana` visualize metrics from `Prometheus`

## Running JWallet

### 1. Docker compose

Requires

- `docker-compose`

Start / Stop

- start
  `./jwallet-cli.sh up-compose`
- stop
  `./jwallet-cli.sh down-compose`

Access service

- Database
  `localhost:5432`
- Keycloak
  `http://localhost:5050`
- Wallet module
  `http://localhost:3001/wallet`
- Rate module
  `http://localhost:3002/rate`
- Account module
  `http://localhost:3003/account`
- Jaeger tracing
  `http://localhost:16686`
- Prometheus
  `http://localhost:9090`
- Grafana
  `http://localhost:3000`

### 2. Kubernetes

Requires

- [K3D](https://k3d.io) to run kubernetes cluster on docker containers on local machine
- `kubectl` to interact with kubernetes cluster

Start / Stop

- start
  `./jwallet-cli.sh up-kube`
- stop
  `./jwallet-cli.sh down-kube`

Access service

- First get the reverse proxy (Ingress) IP (e.g. 172.28.0.4)

```shell
~> kubectl get ingess
NAME             CLASS    HOSTS                   ADDRESS      PORTS   AGE
postgres         <none>   *.postgres.sslip.io     172.28.0.4   80      36m
keycloak         <none>   *.keycloak.sslip.io     172.28.0.4   80      36m
wallet           <none>   *.wallet.sslip.io       172.28.0.4   80      36m
rate             <none>   *.rate.sslip.io         172.28.0.4   80      36m
account          <none>   *.account.sslip.io      172.28.0.4   80      36m
jaeger           <none>   *.jaeger.sslip.io       172.28.0.4   80      36m
prometheus       <none>   *.prometheus.sslip.io   172.28.0.4   80      36m
grafana          <none>   *.grafana.sslip.io      172.28.0.4   80      18m
```

- Then replace dots with dashes (e.g. `172.28.0.4` to `172-28-0-4`)
- keycloak
  `http://<INGRESS_IP>.keycloak.sslip.io`
- Wallet module
  `http://<INGRESS_IP>.wallet.sslip.io/wallet/` (e.g. `http://172-28-0-4.wallet.sslip.io/wallet/`)
- Jaeger tracing
  `http://<INGRESS_IP>.jaeger.sslip.io`
- Prometheus
  `http://<INGRESS_IP>.prometheus.sslip.io`
- Grafana
  `http://<INGRESS_IP>.grafana.sslip.io`
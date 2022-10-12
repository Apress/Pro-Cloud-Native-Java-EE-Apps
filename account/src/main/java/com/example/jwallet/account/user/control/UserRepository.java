package com.example.jwallet.account.user.control;

import static com.example.jwallet.account.user.entity.User.LOAD_USERS_BY_CONVERSIONS;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.jwallet.account.user.entity.User;
import com.example.jwallet.account.user.entity.UserResponse;
import com.example.jwallet.account.user.entity.UserSearchRequest;
import com.example.jwallet.core.control.BaseRepository;
import com.example.jwallet.core.entity.SearchResult;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class UserRepository extends BaseRepository<User, Long> {

	public UserRepository() {
		super(User.class);
	}

	@Override
	public User findById(final Long id) {
		final User user = super.findById(id);
		if (user == null) {
			throw new UserNotFoundException();
		}
		return user;
	}

	public List<User> loadAllUsers() {
		return em.createNamedQuery(User.LOAD_ALL_USERS, User.class).getResultList();
		//        TypedQuery<User> query = em.createQuery("select u from User  u", User.class);
		//        return query.getResultList();

	}

	public List<User> loadUsersByTotalConversion(final BigDecimal totalConversion) {
		return em.createNamedQuery(LOAD_USERS_BY_CONVERSIONS, User.class)
				.setParameter("totalConversions", totalConversion)
				.getResultList();
	}

	public List<User> loadUsersByConversionCriteria(final BigDecimal totalConversion) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
		Root<User> from = query.from(User.class);
		CriteriaQuery<User> where = query.select(from)
				.where(criteriaBuilder
						.greaterThanOrEqualTo(from.get("totalConversions"), totalConversion));
		return em.createQuery(where).getResultList();

	}

	public SearchResult<UserResponse> searchUsers(final UserSearchRequest searchRequest) {
		//TODO --> refactor into smaller methods
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
		Root<User> from = query.from(User.class);
		final Set<Predicate> predicateSet = new HashSet<>();

		if (searchRequest.getFirstName() != null) {
			Predicate firstName = criteriaBuilder.like(from.get("firstName"), "%" + searchRequest.getFirstName() + "%");
			predicateSet.add(firstName);

		}
		if (searchRequest.getLastName() != null) {
			Predicate lastName = criteriaBuilder.like(from.get("lastName"), "%" + searchRequest.getLastName() + "%");
			predicateSet.add(lastName);

		}
		if (searchRequest.getTotalConversions() != null) {
			if (searchRequest.getConversionFilter() == UserSearchRequest.ConversionFilter.EQ) {
				predicateSet.add(criteriaBuilder.equal(from.get("totalConversions"),
						searchRequest.getTotalConversions()));
			}
			if (searchRequest.getConversionFilter() == UserSearchRequest.ConversionFilter.GT) {
				predicateSet.add(criteriaBuilder.greaterThan(from.get("totalConversions"),
						searchRequest.getTotalConversions()));
			}
			if (searchRequest.getConversionFilter() == UserSearchRequest.ConversionFilter.GTE) {
				predicateSet.add(criteriaBuilder.greaterThanOrEqualTo(from.get("totalConversions"),
						searchRequest.getTotalConversions()));
			}

			if (searchRequest.getConversionFilter() == UserSearchRequest.ConversionFilter.LT) {
				predicateSet.add(criteriaBuilder.lessThan(from.get("totalConversions"),
						searchRequest.getTotalConversions()));
			}
			if (searchRequest.getConversionFilter() == UserSearchRequest.ConversionFilter.LTE) {
				predicateSet.add(criteriaBuilder.lessThanOrEqualTo(from.get("totalConversions"),
						searchRequest.getTotalConversions()));
			}

			if (searchRequest.getConversionFilter() == UserSearchRequest.ConversionFilter.NEQ) {
				predicateSet.add(criteriaBuilder.notEqual(from.get("totalConversions"),
						searchRequest.getTotalConversions()));
			}
		}

		if (searchRequest.getUserType() != null) {
			Predicate userType = criteriaBuilder.equal(from.get("userType"), searchRequest.getUserType());
			predicateSet.add(userType);
		}

		CriteriaQuery<User> where = query.select(from).where(predicateSet.toArray(new Predicate[] {}));
		TypedQuery<User> typedQuery = em.createQuery(where);
		if (searchRequest.getOffset() != null) {
			typedQuery = typedQuery.setFirstResult(searchRequest.getOffset());
		}
		if (searchRequest.getLimit() != null) {
			typedQuery = typedQuery.setMaxResults(searchRequest.getLimit());
		}
		List<UserResponse> response =
				typedQuery.getResultList().stream().map(UserService::toUserResponse).collect(Collectors.toList());
		SearchResult<UserResponse> searchResult = new SearchResult<>();
		searchResult.setSearchResult(response);

		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<User> userCountRoot = countQuery.from(User.class);
		countQuery.select(criteriaBuilder.countDistinct(userCountRoot.get("id")))
				.where(predicateSet.toArray(new Predicate[] {}));
		Long count = em.createQuery(countQuery).getSingleResult();

		searchResult.setTotalSize(count);
		searchResult.setLimit(Long.valueOf(searchRequest.getLimit()));
		searchResult.setOffset(searchResult.getOffset());

		return searchResult;


	}

	public List<User> loadAllUsersCriteria() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
		Root<User> from = query.from(User.class);
		return em.createQuery(query.select(from)).getResultList();
	}
}

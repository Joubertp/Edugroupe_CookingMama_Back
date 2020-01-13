package com.edugroupe.demo.repositories.custom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.projection.ProjectionFactory;

import com.edugroupe.demo.metiers.IngredientRecette;
import com.edugroupe.demo.metiers.Recette;

public class RecetteRepositoryImpl implements RecetteRepositoryCustom {

	private static String LINE_BREAK = "\n";

	@PersistenceContext
	private EntityManager em;

	private final ProjectionFactory projectionFactory;
	
	@Autowired
	public RecetteRepositoryImpl(ProjectionFactory projectionFactory) {
		this.projectionFactory = projectionFactory;
	}
	
	@Override
	public <T>Page<T> findByCritere(Recette critere, Pageable pageable, Class<T> type) {

		if (critere == null || pageable == null)
			throw new NullPointerException("L'un des argument de findByCritere() dans RecetteRepositoryImpl est null");

		int pageSize = pageable.getPageSize();
		Sort sortList = pageable.getSort();
		HashMap<String, Integer> integers_param = new HashMap<>();
		HashMap<String, String> string_param = new HashMap<>();

		String query = creatQuery_FindByCritere(critere, integers_param, string_param, sortList);
		int pageMax = getNumberPageOfQuery(query, integers_param, string_param, pageSize);
		List<Recette> results = getResultQuery(query, integers_param, string_param, pageable);
		
		List<T> resultsProjection = new ArrayList<>();
		for(Recette r : results) {
			T projection = projectionFactory.createProjection(type,r);
			resultsProjection.add(projection);
		}
		
		return new PageImpl<>(resultsProjection, pageable, pageMax);
	}

	private String creatQuery_FindByCritere(Recette critere, HashMap<String, Integer> integers_param,
			HashMap<String, String> string_param, Sort sortList) {
		AtomicBoolean isSetedWhere = new AtomicBoolean(false);

		String query = "Select r" + LINE_BREAK + "from Recette AS r";

		if (critere.getIngredients() != null && critere.getIngredients().size() != 0) {
			query += LINE_BREAK + "JOIN r.ingredients AS ingrRef" + LINE_BREAK + "JOIN ingrRef.ingredient AS i";
		}

		if (critere.getNom() != null && !critere.getNom().isEmpty()) {
			query += LINE_BREAK + whereOrAnd(isSetedWhere)+" r.nom LIKE :paramNomRecette";
			string_param.put("paramNomRecette", "%"+critere.getNom()+"%");
		}

		if (critere.getIngredients() != null && critere.getIngredients().size() != 0) {
			Set<IngredientRecette> listIngredients = critere.getIngredients();
			query += LINE_BREAK + whereOrAnd(isSetedWhere);
			int i = 1;
			for (IngredientRecette ingrRef : listIngredients) {
				if (i == 1)
					query += LINE_BREAK + "(i.id = :ingreId" + i;
				else
					query += LINE_BREAK + "OR i.id = :ingreId" + i;
				integers_param.put("ingreId" + i, ingrRef.getIngredient().getId());
				i++;
			}
			query += ")" + LINE_BREAK + "Group By r.id" + LINE_BREAK + "Having Count(distinct i) = "
					+ listIngredients.size();
		}
		Iterator<Order> sortIter = sortList.iterator();

		while (sortIter.hasNext()) {
			Order order = sortIter.next();
			query += LINE_BREAK + "ORDER BY r." + order.getProperty() + " " + order.getDirection();
		}

		return query;
	}

	private int getNumberPageOfQuery(String query, HashMap<String, Integer> integers_param,
			HashMap<String, String> string_param, int pageSize) {
		TypedQuery<Recette> typedquery = em.createQuery(query, Recette.class);

		for (Entry<String, Integer> me : integers_param.entrySet()) {
			typedquery.setParameter(me.getKey(), me.getValue());
		}
		for (Entry<String, String> me : string_param.entrySet()) {
			typedquery.setParameter(me.getKey(), me.getValue());
		}
		
		return typedquery.getResultList().size();
	}

	private List<Recette> getResultQuery(String query, HashMap<String, Integer> integers_param,
			HashMap<String, String> string_param, Pageable pageable) {
		int pageSize = pageable.getPageSize();
		int pageStart = pageable.getPageNumber() * pageSize;
		TypedQuery<Recette> typedquery = em.createQuery(query, Recette.class)
				.setFirstResult(pageStart)
				.setMaxResults(pageSize);

		for (Entry<String, Integer> me : integers_param.entrySet()) {
			typedquery.setParameter(me.getKey(), me.getValue());
		}
		for (Entry<String, String> me : string_param.entrySet()) {
			typedquery.setParameter(me.getKey(), me.getValue());
		}

		return typedquery.getResultList();
	}

	private String whereOrAnd(AtomicBoolean isSetedWhere) {
		if (isSetedWhere.get()) {
			return "and";
		} else {
			isSetedWhere.set(true);
			return "where";
		}

	}

}

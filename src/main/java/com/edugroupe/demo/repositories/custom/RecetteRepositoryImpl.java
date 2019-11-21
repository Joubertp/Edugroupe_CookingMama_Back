package com.edugroupe.demo.repositories.custom;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.edugroupe.demo.metiers.IngredientRecette;
import com.edugroupe.demo.metiers.Recette;

public class RecetteRepositoryImpl implements RecetteRepositoryCustom{

	private static String LINE_BREAK = "\n";
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Page<Recette> findByCritere(Recette critere, Pageable pageable) {
		
		if(critere == null || pageable == null)
			throw new NullPointerException("L'un des argument de findByCritere() dans RecetteRepositoryImpl est null");
		
		int pageSize = pageable.getPageSize();
		int pageStart = pageable.getPageNumber()*pageSize;
		HashMap<String,Integer> parameters = new HashMap<>();	    

		String query = creatQuery_FindByCritere(critere,parameters);
		
		int pageMax = getNumberPageOfQuery(query,parameters,pageSize);
				
		List<Recette> result = getResultQuery(query, parameters, pageSize,  pageStart);
		
		return new PageImpl<>(result, pageable ,pageMax);
	}
	
	private String creatQuery_FindByCritere(Recette critere, HashMap<String,Integer> parameters) {
		AtomicBoolean isSetedWhere = new AtomicBoolean(false);
		
		String query = "Select r" 
				+ LINE_BREAK + "from Recette AS r"
				+ LINE_BREAK + "JOIN r.ingredients AS ingrRef"
				+ LINE_BREAK + "JOIN ingrRef.ingredient AS i";

		if(critere.getIngredients() != null && critere.getIngredients().size() != 0) {
			Set<IngredientRecette> listIngredients = critere.getIngredients();
			query += LINE_BREAK + whereOrAnd(isSetedWhere);
			int i = 1;
			for(IngredientRecette ingrRef : listIngredients) {
				if(i == 1)
					query += LINE_BREAK + "(i.id = :ingreId"+i;
				else
					query += LINE_BREAK + "OR i.id = :ingreId"+i;
				parameters.put("ingreId"+i, ingrRef.getIngredient().getId());
				i++;
			}
			query += ")"
					+LINE_BREAK + "Group By r.id"
					+LINE_BREAK + "Having Count(distinct i) = "+listIngredients.size();
			
		}
		
		return query;
	}
	
	private int getNumberPageOfQuery(String query, HashMap<String,Integer> parameters, int pageSize) {		
		TypedQuery<Recette> typedquery = em.createQuery(query, Recette.class);
		
			for (Entry<String, Integer> me : parameters.entrySet()) {
			      typedquery.setParameter(me.getKey(), me.getValue());
			    }
		
		return typedquery.getResultList().size();
	}
	
	private List<Recette> getResultQuery(String query, HashMap<String,Integer> parameters, int pageSize, int pageStart){
		TypedQuery<Recette> typedquery = em.createQuery(query, Recette.class)
			.setFirstResult(pageStart)
			.setMaxResults(pageSize);
	
		for (Entry<String, Integer> me : parameters.entrySet()) {
		      typedquery.setParameter(me.getKey(), me.getValue());
		    }
		
		return typedquery.getResultList();
	}

	private String whereOrAnd(AtomicBoolean isSetedWhere) {
		if(isSetedWhere.get()) {
			return "and";
		}
		else {
			isSetedWhere.set(true);
			return "where";
		}
		
	}
	
}

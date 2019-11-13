package com.edugroupe.demo.repositories.custom;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
	public Page<Recette> findByCritere(Recette critere, Pageable page) {
		if(critere == null || page == null)
			throw new NullPointerException("L'un des argument de findByCritere() dans RecetteRepositoryImpl est null");
		
		int size = page.getPageSize();
		int start = page.getPageNumber()*size;
		AtomicBoolean isSetedWhere = new AtomicBoolean(false);
		HashMap<String,Integer> parameters = new HashMap<>();	    

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
		
		TypedQuery<Recette> typedquery = em.createQuery(query, Recette.class)
				.setFirstResult(start)
				.setMaxResults(size);
		
		for (Entry<String, Integer> me : parameters.entrySet()) {
	          typedquery.setParameter(me.getKey(), me.getValue());
	        }
		
		List<Recette> result = typedquery.getResultList();

		return new PageImpl<>(result);
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

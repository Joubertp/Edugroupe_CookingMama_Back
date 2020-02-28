package com.edugroupe.demo.metiers.DTO;

import java.util.Optional;
import java.util.Set;

import com.edugroupe.demo.metiers.Recette;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class RecetteDTO {
	private Recette recette;
	private Optional<Set<IngredientRecetteDTO>> ingredients;
}

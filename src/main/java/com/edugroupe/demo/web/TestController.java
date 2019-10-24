package com.edugroupe.demo.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edugroupe.demo.constante.CathIngre;
import com.edugroupe.demo.metiers.Ingredient;
import com.edugroupe.demo.metiers.IngredientRecette;
import com.edugroupe.demo.metiers.Recette;
import com.edugroupe.demo.metiers.json.EtapeRecette;
import com.edugroupe.demo.repositories.IngredientRecetteRepository;
import com.edugroupe.demo.repositories.IngredientRepository;
import com.edugroupe.demo.repositories.RecetteRepository;
import com.edugroupe.demo.repositories.UserRepository;

@Controller
@RequestMapping("test")
public class TestController {

	@Autowired
	private RecetteRepository recetteRep;
	@Autowired
	private IngredientRepository ingredientRep;
	@Autowired
	private IngredientRecetteRepository ingredientRecetteRep;
	@Autowired
	private UserRepository userRep;

	@RequestMapping("/greeting")
	public @ResponseBody String gretting() {
		return "Hello World!";
	}

	@GetMapping(value = "/InsertTestBDD", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	@CrossOrigin("http://localhost:4200")
	public List<Object> InsertTestData() {

		List<Object> toReturn = new ArrayList<>();
		toReturn.add(this.insertDataIngredient());
		toReturn.add(this.insertDataRecette());
		
		return toReturn;
	}
	
	public List<Recette> insertDataRecette(){
		Recette recette1 = new Recette();
		List<Recette> toReturn = new ArrayList<>();

		recette1.setDateCreation(LocalDate.now());
		recette1.setNom("Soupe au lardon");
		recette1.setTempsPreparation(50);
		recette1.setTempsCuisson(80);
		recette1.setDescription("Une soupe bien grasse et bien degeulasse, que je déconseil fortement de manger.");


		EtapeRecette erecette1_1 = new EtapeRecette(1);
		erecette1_1.setDescription("portez à ébulition l'eau dans un casserol");
		EtapeRecette erecette1_2 = new EtapeRecette(2);
		erecette1_2.setDescription("ajoutez les lardon");
		EtapeRecette erecette1_3 = new EtapeRecette(3);
		erecette1_3.setDescription("utilisez un mixeur pour broyer les lardon");

		Set<EtapeRecette> listeEtape = Stream.of(erecette1_1, erecette1_2, erecette1_3).collect(Collectors.toSet());
		recette1.setListeEtapes(listeEtape);

		recette1 = recetteRep.save(recette1);
		
		IngredientRecette ingrediantRecette1 = new IngredientRecette();
		ingrediantRecette1.setIngredient(new Ingredient(1));
		ingrediantRecette1.setRecette(recette1);
		ingrediantRecette1.setValeur("500g de ");
		
		ingrediantRecette1 = ingredientRecetteRep.save(ingrediantRecette1);
		
		toReturn.add(recetteRep.findById(recette1.getId()).get());
		
		return toReturn;
	}
	
	public List<Ingredient> insertDataIngredient(){
		
		List<Ingredient> ingredients = Arrays.asList(
				new Ingredient(0, "lardon", 
						CathIngre.VIANDE,null,
						"C'est meilleur avec des pates",null),
				new Ingredient(0, "pâte à pizza",
						CathIngre.PATE, null, 
						"Une pizza les yeux fermés ? C'est possible grâce à la pâte à pizza Herta Ronde & Fine ! Quelques cuilliérées de sauce tomate ou de crème, une poignée d'ingrédients et le tour est joué ! Dorée au four, c'est un délice…",
						null),
				new Ingredient(0, "fromage de chèvre long", 
						CathIngre.FROMAGE, null
						, "Au lait cru, faiblement emprésuré lors d'une courte maturation, moulé à la louche dans de hautes faisselles souvent réhaussées, retourné manuellement deux fois par jour et légèrement salé, démoulé, affiné sur claies en hâloir",
						null),
				new Ingredient(0, "coulis de tomate", 
						CathIngre.COULIS, null, 
						"Un coulis est un suc obtenu à froid après avoir écrasé un aliment qui est ensuite passé dans un tamis. Il sert généralement de sauce ou d'accompagnement. Ne pas confondre le coulis et la purée qui, elle, est cuite.", 
						null),						
				new Ingredient(0, "roquefort", 
						CathIngre.FROMAGE, null,
						"Roquefort ou ròcafòrt en occitan rouergat est une appellation d'origine désignant un fromage français à pâte persillée élaborée exclusivement avec des laits crus de brebis.", 
						null),
				new Ingredient(0, "mozzarella", 
						CathIngre.FROMAGE, null, 
						"La mozzarella, ou mozzarelle, est un fromage à pâte filée d'origine italienne à base de lait de vache ou de bufflonne. La mozzarella di Bufala Campana, produite en Campanie avec du lait de bufflonne, est l'objet d'une appellation d'origine protégée (AOP) depuis 1996.", 
						null),
				new Ingredient(0, "gruyère râpé", 
						CathIngre.FROMAGE, null, 
						"Le fromage râpé est une forme de présentation du fromage qui a subi un processus de râpage. On utilise généralement à cet effet des fromages à pâte dure et vieillis. Une râpe à fromage peut être employée pour râper le fromage manuellement, mais on peut aussi acheter du fromage déjà râpé dans le commerce.", 
						null),
				new Ingredient(0, "poivre", 
						CathIngre.EPICE, null, 
						"Le poivre est une épice obtenue à partir des baies de différentes espèces de poivriers, des plantes de la famille des pipéracées. En France, seuls ont droit légalement à l'appellation de « poivre » sans plus de précision les fruits du poivrier noir (Piper nigrum)1 qui donnent le poivre vert, blanc, rouge ou noir (le poivre vient de notre moulin).", 
						null),
				new Ingredient(0, "sel", 
						CathIngre.EPICE, null, 
						"Le terme sel peut désigner le sel utilisé en alimentation, composé essentiellement de chlorure de sodium NaCl, et principalement produit à partir de sel gemme ou de sel de mer. Il est utilisé comme condiment ou comme agent de conservation ou de préparation dans l'industrie agroalimentaire.", 
						null),
				new Ingredient(0, "viande de veau", 
						CathIngre.VIANDE, null, 
						"La viande de veau, ou plus simplement « veau », est une viande issue de bovins juvéniles, par opposition à la viande de bœuf provenant de bovins plus âgés. Cette viande provient généralement de bovins non sevrés de sexe mâle, âgés en principe de moins de huit mois. Classée dans les viandes blanches, elle est généralement plus chère que la viande de bœuf.",
						null),
				new Ingredient(0, "bouillon de légumes", 
						CathIngre.BOUILLON, null, 
						"Le bouillon est le liquide de cuisson du bœuf, d'une volaille, ou de légumes. On peut le consommer tel quel, mais le plus souvent on l'utilise pour préparer des sauces, généralement on peut utiliser des cubes de concentrés que l'on fait fondre à l'eau bouillante", 
						null),
				new Ingredient(0, "carotte", 
						CathIngre.LEGUME, null, 
						"La carotte, Daucus carota subsp. sativus, est une plante bisannuelle de la famille des apiacées (aussi appelées ombellifères), largement cultivée pour sa racine pivotante charnue, comestible, de couleur généralement orangée, consommée comme légume. La carotte représente après la pomme de terre le principal légume-racine cultivé dans le monde.",
						null),
				new Ingredient(0, "oigon jaune",
						CathIngre.LEGUME, null,
						"L’oignon jaune, parfois appelé oignon paille, est le bulbe d’une plante potagère de la famille des alliacés et qui porte le même nom. Le bulbe de l’oignon est constitué de feuilles claires, très charnues, juteuses et fortes en goût, recouvertes de fines pelures d’un jaune plus ou moins foncé. Il est très utilisé dans toutes les cuisines du monde car il pousse partout et est cultivé depuis plus de 5000 ans. L’oignon jaune est un des aromates le plus employé mais il se cuisine aussi comme légume. Contrairement à l'oignon blanc qui est de courte saison et fragile, l’oignon jaune est commercialisé toute l'année et se conserve très bien.",
						null)
				
				);
				
		for(Ingredient i : ingredients) {
			ingredientRep.save(i);
		}
				
		return ingredients;		
	}
}

package com.edugroupe.demo.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
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

	private Random random = new Random();
	
	@RequestMapping("/greeting")
	public @ResponseBody String gretting() {
		return "Hello World!";
	}

	/*
	 * Inser une liste de donnee de test
	 */
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

		/*
		 * Liste des etapes
		 */
		EtapeRecette erecette1_1 = new EtapeRecette(1);
		erecette1_1.setDescription("portez à ébulition l'eau dans une casserol");
		EtapeRecette erecette1_2 = new EtapeRecette(2);
		erecette1_2.setDescription("ajoutez les lardon");
		EtapeRecette erecette1_3 = new EtapeRecette(3);
		erecette1_3.setDescription("utilisez un mixeur pour broyer les lardon");

		Set<EtapeRecette> listeEtape = Stream.of(erecette1_1, erecette1_2, erecette1_3).collect(Collectors.toSet());
		recette1.setListeEtapes(listeEtape);

		/*
		 * enregistrement de la recette 
		 */
		recette1 = recetteRep.save(recette1);
		
		/*
		 * Liste des Ingredients de la recettes
		 */
		IngredientRecette ingrediantRecette1 = new IngredientRecette();
		ingrediantRecette1.setIngredient(new Ingredient(1));
		ingrediantRecette1.setRecette(recette1);
		ingrediantRecette1.setValeur("500g de ");
		
		/*
		 * Enregirstrement des ingredients
		 */
		ingrediantRecette1 = ingredientRecetteRep.save(ingrediantRecette1);
		
		toReturn.add(recetteRep.findById(recette1.getId()).get());
		
		/*
		 * Creation est Insertion de Recettes aleatoires
		 */
		
		int nombre_de_recettes = 40;
		List<Recette> randomRecettes = generatorRecettes(nombre_de_recettes);
		for(int i = 0 ; i < nombre_de_recettes; i++) {
			Recette randomRecette = randomRecettes.get(i);
			randomRecette = recetteRep.save(randomRecette);
			
			Set<IngredientRecette> randomIngredientRecettes = generatorIngredients(randomRecette.getId(),random.nextInt(10)+3);
			System.out.println(randomIngredientRecettes);
			randomIngredientRecettes.forEach(ir -> ingredientRecetteRep.save(ir));
			toReturn.add(randomRecette);
		}
		
		return toReturn;
	}
	
	public List<Recette> generatorRecettes(int number){
		// Liste d'adjectifs gourmands
		String[] adjGourmands = {"Succulent","Savoureux","Aromatique","Allégé","Goûteux","Long en bouche","Surprenant","Original","Banal","Ordinaire","Fumant","Surgelé","Fumé","Collant","Pâteux","Craquant","Grumeleux","Gluant","Granuleux","Juteux","Croquant","Moelleux","Texture","Goût","Sec","Bon","Mauvais","Délicieux","Acre","Exquis","Fade","Insipide","Acerbe","Rance","Agéable","Aggressif","Dominant","Fort","Fermenté","Gélatineux","Amer","Sucré","Acide","Astringent","Aigre","Doux","Salé","Crémeux","Epais","Onctueux","Cru","Cuit","Léger","Lourd","Fin","Saignant","A point","Ferme","Copieux","Compact","Dense","Mousseux","Aéré","Acidulé","Chaud","Froid","Tiède","Glacé","Brûlant","Bouillant","Affiné","Liquide","Croustillant","Intense","Gras","Farineux","Serré","Poivré","Epicé","Piquant","Relevé","Fondant","Dur","Tendre","Grillé","Velouté","Brûlé","Calciné","Frais","Pasteurisé","Mou","Pétillant","Doré"};
		int imax =  adjGourmands.length;

		List<Recette> recettes = new ArrayList<>();
		
		for(int i = 0;i < number; i++) {
			Recette r = new Recette();
			String nom = adjGourmands[random.nextInt(imax)];
			
			if(isVowel(nom.charAt(0)))
				nom = "L'"+ nom;
			else
				nom = "Le "+ nom;
				
			nom += " " + adjGourmands[random.nextInt(imax)];
			
			r.setNom(nom);
			r.setTempsPreparation(random.nextInt(200));
			r.setTempsCuisson(random.nextInt(200));
			r.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
			
			r.setListeEtapes(generatorEtapeRecette(random.nextInt(10)+5));
			
			recettes.add(r);
		}
		
		return recettes;
		
	}
	
	public Set<EtapeRecette> generatorEtapeRecette(int number) {
		String[] actions  = {"Additionner","Ajouter","Allonger","Amener","Aplatir","Aromatiser","Arroser","Assaisonner","Assouplir","Badigeonner","Battre","Beurrer","Blanchir","Bouillir","Braiser","Brider","Chauffer","Chemiser","Chiffonner","Choisir","Ciseler","Coller","Colorer","Compléter","Compter","Concasser","Confectionner","Couper","Couvrir","Cuire","Débarrasser","Débiter","Décoquiller","Décorer","Décortiquer","Dégermer","Déglacer","Dégorger","Délayer","Démouler","Dénoyauter","Désosser","Dessaler","Dessécher","Détailler","Détendre","Diluer","Disposer","Dissoudre","Donner","Dorer","Dresser","Ébarber","Ébouillanter","Écailler","Écraser","Écumer","Effeuiller","Effiler","Effilocher","Égoutter","Égrapper","Égrener","Émincer","Emplir","Émulsionner","Enfermer","Enfourner","Entreposer","Envelopper","Épaissir","Épépiner","Éponger","Équeuter","Essorer","Essuyer","Étaler","Étendre","Étuver","Évider","Façonner","Farcir","Fariner","Fendre","Ficeler","Filtrer","Flamber","Foncer","Fondre","Fouetter","Fourrer","Frémir","Frire","Froisser","Frotter","Garnir","Glacer","Gonfler","Gratiner"};
		String[] ingedient = {"le beurre","la crème fraîche liquide","la crème fraîche semi épaisse","le gruyère râpé","les œufs","le fromage blanc","les yaourts nature","un fromage à pâte dur","le lait","la moutarde","le citron","pâte à tarte brisée prête à dérouler","le jambon","la salade ou de la mâche (elle accompagne aussi bien les tartes, quiches salées que les plats d’hiver comme tartiflette…)","les pâtes","le riz","la semoule ou du boulgour","les boîtes de champignons de paris émincés ou entiers","le thon au naturel","la pulpe de tomates","les tomates pelées"," si vous avez un jardin, mettez en bocaux les tomates entières, elles seront ramollies dues à la stérilisation à chaud les bocaux mais elles peuvent remplacer les tomates pelées dans les préparations l’hiver.","les lentilles (en boîtes ou sèches)","le coulis de tomate","un pot de tomates séchées","le concentré de tomates","les cubes de bouillon de volaille","les cubes de bouillon de bœuf","les cubes de bouillon de légumes","l’huile d’olive","l’huile de tournesol ou autre (colza, pépins de raisins..)","le vinaigre balsamique ou vinaigre de vin","le court-bouillon","le vin blanc pour cuisiner","oignons","ail","échalotes","les pommes de terre","les fruits et les légumes de saisons","la farine","le sucre blanc ou roux","la fécule de maïs ou maïzena","la levure","le sucre vanillé","les raisins secs","  un pot de miel","une tablette de chocolat pour la cuisine","les gâteaux secs comme les petits beurres ou les speculoos"," feuilles de laurier","branches de thym","herbes de Provence"," coriandre moulue"," paprika"," noix de muscade ou muscade moulue","clous de girofle","cannelle","aneth","curry"};
		
		Set<EtapeRecette> etapeRecettes = new HashSet<>();
		for(int i = 0; i < number; i++) {
			EtapeRecette etapeRecette = new EtapeRecette();
			String description = actions[random.nextInt(actions.length)]
			                             + " " + ingedient[random.nextInt(ingedient.length)];
			etapeRecette.setDescription(description);
			etapeRecette.setNumero(i+1);
			etapeRecettes.add(etapeRecette);
		}
		System.out.println("Etapes recette : "+ etapeRecettes);
		
		return etapeRecettes;
	}
	
	public static boolean isVowel(char c) {
		  return "AEIOUY".indexOf(c) != -1;
		}
	
	public Set<IngredientRecette> generatorIngredients(int idRecette, int number){
		Set<IngredientRecette> ingredients = new HashSet<>();
		
		for(int i = 0; i < number; i++) {
			IngredientRecette ingredientRecette = new IngredientRecette();
			Ingredient ingredient = new Ingredient();
			ingredient.setId(random.nextInt(13)+1);
			ingredientRecette.setIngredient(ingredient);
			ingredientRecette.setValeur("3T de/d' ");
			ingredients.add(ingredientRecette);
		}
		
		return ingredients;
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

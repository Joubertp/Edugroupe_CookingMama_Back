package com.edugroupe.demo.utils;

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
import org.springframework.stereotype.Controller;

import com.edugroupe.demo.constantes.CathIngre;
import com.edugroupe.demo.metiers.Ingredient;
import com.edugroupe.demo.metiers.IngredientRecette;
import com.edugroupe.demo.metiers.Recette;
import com.edugroupe.demo.metiers.User;
import com.edugroupe.demo.metiers.json.EtapeRecette;
import com.edugroupe.demo.repositories.IngredientRecetteRepository;
import com.edugroupe.demo.repositories.IngredientRepository;
import com.edugroupe.demo.repositories.RecetteRepository;
import com.edugroupe.demo.repositories.UserRepository;

@Controller
public class DataGenerator {

	private Random random = new Random();
	
	@Autowired	private RecetteRepository recetteRep;
	@Autowired	private IngredientRepository ingredientRep;
	@Autowired	private IngredientRecetteRepository ingredientRecetteRep;
	@Autowired	private UserRepository userRep;
	

	public List<Recette> creatAndSaveDataRecette(){		
		// recupération des recettes personalisé
		List<Recette> toReturn = saveRecettePersonalise();
				
		/*
		 * Creation et Insertion de Recettes aleatoires
		 */		
		int nombre_de_recettes = 100;
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
		
		Iterable<User> itAuteurs = userRep.findAll();
		List<User> auteurs = new ArrayList<>();
		for(User a : itAuteurs) {
			auteurs.add(a);
		}		
		int auteurMax = auteurs.size() - 1;
		
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
			r.setDateCreation(createRandomDate(1900, 2019));
			r.setDateDerniereEdition(r.getDateCreation());
			r.setAuteur(auteurs.get(random.nextInt(auteurMax)+1));
			
			recettes.add(r);
		}
		
		return recettes;
		
	}
	
	public Set<EtapeRecette> generatorEtapeRecette(int number) {
		String[] actions  = {"Additionner","Ajouter","Allonger","Amener","Aplatir","Aromatiser","Arroser","Assaisonner","Assouplir","Badigeonner","Battre","Beurrer","Blanchir","Bouillir","Braiser","Brider","Chauffer","Chemiser","Chiffonner","Choisir","Ciseler","Coller","Colorer","Compléter","Compter","Concasser","Confectionner","Couper","Couvrir","Cuire","Débarrasser","Débiter","Décoquiller","Décorer","Décortiquer","Dégermer","Déglacer","Dégorger","Délayer","Démouler","Dénoyauter","Désosser","Dessaler","Dessécher","Détailler","Détendre","Diluer","Disposer","Dissoudre","Donner","Dorer","Dresser","Ébarber","Ébouillanter","Écailler","Écraser","Écumer","Effeuiller","Effiler","Effilocher","Égoutter","Égrapper","Égrener","Émincer","Emplir","Émulsionner","Enfermer","Enfourner","Entreposer","Envelopper","Épaissir","Épépiner","Éponger","Équeuter","Essorer","Essuyer","Étaler","Étendre","Étuver","Évider","Façonner","Farcir","Fariner","Fendre","Ficeler","Filtrer","Flamber","Foncer","Fondre","Fouetter","Fourrer","Frémir","Frire","Froisser","Frotter","Garnir","Glacer","Gonfler","Gratiner"};
		String[] ingedient = {"le beurre","la crème fraîche liquide","la crème fraîche semi épaisse","le gruyère râpé","les œufs","le fromage blanc","les yaourts nature","un fromage à pâte dur","le lait","la moutarde","le citron","la pâte à tarte brisée prête à dérouler","le jambon","la salade ou de la mâche (elle accompagne aussi bien les tartes, quiches salées que les plats d’hiver comme tartiflette…)","les pâtes","le riz","la semoule ou du boulgour","les boîtes de champignons de paris émincés ou entiers","le thon au naturel","la pulpe de s","les tomates pelées","le coulis de tomate","un pot de tomates séchées","le concentré de tomates","les cubes de bouillon de volaille","les cubes de bouillon de bœuf","les cubes de bouillon de légumes","l’huile d’olive","l’huile de tournesol ou autre (colza, pépins de raisins..)","le vinaigre balsamique ou vinaigre de vin","le court-bouillon","le vin blanc pour cuisiner","les oignons","l'ail","les échalotes","les pommes de terre","les fruits et les légumes de saisons","la farine","le sucre blanc ou roux","la fécule de maïs ou maïzena","la levure","le sucre vanillé","les raisins secs","  un pot de miel","une tablette de chocolat pour la cuisine","les gâteaux secs comme les petits beurres ou les speculoos","les feuilles de laurier","les branches de thym","les herbes de Provence","le coriandre moulue","le paprika","les noix de muscade ou muscade moulue","les clous de girofle","la cannelle","l'aneth","le curry"};
		
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
	
	public boolean isVowel(char c) {
		  return "AEIOUYaeiouyéè".indexOf(c) != -1;
		}
	
	public Set<IngredientRecette> generatorIngredients(int idRecette, int number){
		String[] descriptions = {" tones","00 g", " cubes"," demi molles", "bouquet","00 l"," pincés"};
		Set<IngredientRecette> ingredients = new HashSet<>();
		int nbrIngredients = (int) ingredientRep.count();
		
		
		for(int i = 0; i < number; i++) {
			IngredientRecette ingredientRecette = new IngredientRecette();
			Ingredient ingredient = ingredientRep.findById(random.nextInt(nbrIngredients)+1).get();
			String descriptionIngredient = descriptions[random.nextInt(descriptions.length)];
			
			if(isVowel(ingredient.getNom().charAt(0)))
				descriptionIngredient += " d' ";
			else
				descriptionIngredient += " de ";
			
			descriptionIngredient += ingredient.getNom();
			
			
			ingredientRecette.setIngredient(ingredient);				
			ingredientRecette.setRecette(new Recette(idRecette));
			ingredientRecette.setQuantite(random.nextInt(10)+1);
			ingredientRecette.setText(descriptionIngredient);
									
			ingredients.add(ingredientRecette);
			
		}
		
		return ingredients;
	}
	
	public List<Ingredient> creatAndSaveDataIngredient(){
		
		List<Ingredient> ingredients = Arrays.asList(
				new Ingredient( "lardon", 
						"C'est meilleur avec des pates",
						CathIngre.VIANDE),
				new Ingredient( "pâte à pizza",
						"Une pizza les yeux fermés ? C'est possible grâce à la pâte à pizza Herta Ronde & Fine ! Quelques cuilliérées de sauce tomate ou de crème, une poignée d'ingrédients et le tour est joué ! Dorée au four, c'est un délice…",
						CathIngre.PATE),
				new Ingredient( "fromage de chèvre long", 
						"Au lait cru, faiblement emprésuré lors d'une courte maturation, moulé à la louche dans de hautes faisselles souvent réhaussées, retourné manuellement deux fois par jour et légèrement salé, démoulé, affiné sur claies en hâloir",
						CathIngre.FROMAGE),
				new Ingredient( "coulis de tomate", 
						"Un coulis est un suc obtenu à froid après avoir écrasé un aliment qui est ensuite passé dans un tamis. Il sert généralement de sauce ou d'accompagnement. Ne pas confondre le coulis et la purée qui, elle, est cuite.",
						CathIngre.COULIS),						
				new Ingredient( "roquefort", 
						"Roquefort ou ròcafòrt en occitan rouergat est une appellation d'origine désignant un fromage français à pâte persillée élaborée exclusivement avec des laits crus de brebis.",
						CathIngre.FROMAGE),
				new Ingredient( "mozzarella", 
						"La mozzarella, ou mozzarelle, est un fromage à pâte filée d'origine italienne à base de lait de vache ou de bufflonne. La mozzarella di Bufala Campana, produite en Campanie avec du lait de bufflonne, est l'objet d'une appellation d'origine protégée (AOP) depuis 1996.",
						CathIngre.FROMAGE),
				new Ingredient( "gruyère râpé", 
						"Le fromage râpé est une forme de présentation du fromage qui a subi un processus de râpage. On utilise généralement à cet effet des fromages à pâte dure et vieillis. Une râpe à fromage peut être employée pour râper le fromage manuellement, mais on peut aussi acheter du fromage déjà râpé dans le commerce.",
						CathIngre.FROMAGE),
				new Ingredient( "poivre", 
						"Le poivre est une épice obtenue à partir des baies de différentes espèces de poivriers, des plantes de la famille des pipéracées. En France, seuls ont droit légalement à l'appellation de « poivre » sans plus de précision les fruits du poivrier noir (Piper nigrum)1 qui donnent le poivre vert, blanc, rouge ou noir (le poivre vient de notre moulin).",
						CathIngre.EPICE,CathIngre.CONDIMENT),
				new Ingredient( "sel", 
						"Le terme sel peut désigner le sel utilisé en alimentation, composé essentiellement de chlorure de sodium NaCl, et principalement produit à partir de sel gemme ou de sel de mer. Il est utilisé comme condiment ou comme agent de conservation ou de préparation dans l'industrie agroalimentaire.",
						CathIngre.CONDIMENT),
				new Ingredient( "viande de veau", 
						"La viande de veau, ou plus simplement « veau », est une viande issue de bovins juvéniles, par opposition à la viande de bœuf provenant de bovins plus âgés. Cette viande provient généralement de bovins non sevrés de sexe mâle, âgés en principe de moins de huit mois. Classée dans les viandes blanches, elle est généralement plus chère que la viande de bœuf.",
						CathIngre.VIANDE),
				new Ingredient( "bouillon de légumes", 
						"Le bouillon est le liquide de cuisson du bœuf, d'une volaille, ou de légumes. On peut le consommer tel quel, mais le plus souvent on l'utilise pour préparer des sauces, généralement on peut utiliser des cubes de concentrés que l'on fait fondre à l'eau bouillante",
						CathIngre.BOUILLON),
				new Ingredient( "carotte", 
						"La carotte, Daucus carota subsp. sativus, est une plante bisannuelle de la famille des apiacées (aussi appelées ombellifères largement cultivée pour sa racine pivotante charnue, comestible, de couleur généralement orangée, consommée comme légume. La carotte représente après la pomme de terre le principal légume-racine cultivé dans le monde",
						CathIngre.LEGUME), 
				new Ingredient( "oignon jaune",
						"L’oignon jaune, parfois appelé oignon paille, est le bulbe d’une plante potagère de la famille des alliacés et qui porte le même nom. Le bulbe de l’oignon est constitué de feuilles claires, très charnues, juteuses et fortes en goût, recouvertes de fines pelures d’un jaune plus ou moins foncé. Il est très utilisé dans toutes les cuisines du monde car il pousse partout et est cultivé depuis plus de 5000 ans. L’oignon jaune est un des aromates le plus employé mais il se cuisine aussi comme légume. Contrairement à l'oignon blanc qui est de courte saison et fragile, l’oignon jaune est commercialisé toute l'année et se conserve très bien.",
						CathIngre.LEGUME, CathIngre.CONDIMENT),
				new Ingredient( "moutarde",
						"La moutarde (du latin mustum ardens, « moût ardent ») est un condiment préparé à partir des graines d'une plante de la famille des Brassicaceae, appelée aussi moutarde. Ces graines sont petites, d'un diamètre approximatif de 1 mm. Leur coloration varie entre le blanc jaunâtre et le noir selon les espèces et participent à la teinte du condiment.\r\n" + 
						"\r\n" + 
						"La moutarde est répandue dans plusieurs cuisines régionales et continentales. Sur l'ensemble des épices et condiments, la moutarde est le troisième produit le plus consommé dans le monde après le sel et le poivre. Columelle est le premier à en donner la recette au ier siècle dans son De re rustica (XII, 55). Apicius, au ive siècle, en fournit aussi une recette dans De re coquinaria. En Inde, les graines de moutarde sont souvent cuites entières pour donner une saveur particulière à l'huile. L'huile extraite des graines est utilisée pour la cuisine au Bengale.\r\n" + 
						"\r\n" + 
						"En France, le condiment connu sous le nom de moutarde est constitué des graines (en particulier les blanch), souvent réduites en farine et mélangées à du verjus. D'autres ingrédients peuvent être ajoutés, par exemple du sucre, du miel, du vinaigre, du vin ou du lait. La liste des ingrédients autorisés en France pour les moutardes figure dans le décret 2000-658. Les graines entières peuvent être submergées de liquide avant le meulage si l'on veut fabriquer de la moutarde en grain dite à l'ancienne.",
						CathIngre.SAUCE,CathIngre.EPICE, CathIngre.CONDIMENT),
				new Ingredient( "échalote",
						"L'échalote est une plante bulbeuse de la famille des Amaryllidacées, cultivée comme plante condimentaire et potagère. Le terme désigne aussi le bulbe lui-même, qui fait partie depuis longtemps de la gastronomie française. Au Québec et au Nouveau-Brunswick, elle est appelée « échalotte française » (le mot « échalote » étant plutôt utilisé pour nommer la cébette).\r\n" + 
						"\r\n" + 
						"Nom scientifique : Allium cepa L. var. aggregatum G. Don. (anciennement Allium ascalonicum L., synonyme : Allium hierochuntinum Bois), famille des Amaryllidacées (précédemment Liliacées). L'échalote est tantôt considérée comme une espèce à part, tantôt comme une simple variété d'oignon. Certains auteurs rattachent l'échalote grise à une espèce différente : Allium oschaninii O. Fedtsch., espèce originaire d'Asie centrale (Afghanistan, Iran).\r\n" + 
						"\r\n" + 
						"Étymologie : l'ancien nom de l'échalote dérive du latin ascalonia (cepa) « (oignon) d'Ascalon ». Ascalon est une ville située dans le pays des Philistins dans le territoire actuel d'Israël (aujourd'hui Ashkelon). D'après la légende, les Francs auraient rapporté les échalotes en Occident après le siège d'Ascalon, à la fin de la première croisade. Cet apport tardif semble peu probable car la plante est déjà largement utilisée en Italie dés le Ier siècle et est un des ingrédients communs dans le livre de recettes de Marcus Gavius Apicius, De Re Coquinaria (l'Art culinaire).\r\n" + 
						"\r\n" + 
						"Elle fait aussi partie des plantes dont la culture est recommandée dans les domaines royaux par Charlemagne dans le capitulaire De Villis (fin du viiie ou début du ixe siècle).",
						CathIngre.LEGUME, CathIngre.CONDIMENT),
				new Ingredient( "ail",
						"L'ail, ail commun ou ail cultivé (Allium sativum) est une espèce de plante potagère vivace monocotylédone dont les bulbes, à l'odeur et au goût forts, sont souvent employés comme condiment en cuisine. La partie consommée la tête d'ail se compose de plusieurs caïeux ou « gousses » d'ail.\r\n" + 
						"\r\n" + 
						"On en distingue plusieurs types.\r\n" + 
						"\r\n" + 
						"Originaire d'Asie centrale, il aurait été utilisé depuis 5 000 ans en région méditerranéenne, en particulier en Égypte. Il est aujourd'hui toujours très apprécié dans de nombreuses régions pour ses qualités gustatives et médicinales.",
						CathIngre.LEGUME, CathIngre.CONDIMENT),
				new Ingredient( "oignon",
						"L'oignon ou ognon (Allium cepa prononcé /ɔ.ɲɔ̃/, est une espèce de plantes herbacées bisannuelles de la famille des Amaryllidaceae, largement et depuis longtemps cultivée comme plante potagère pour ses bulbes de saveur et d'odeur fortes ou pour ses feuilles. Le terme désigne aussi le bulbe de cette plante récolté comme légume. Par extension, il désigne parfois familièrement en jardinage les bulbes d'autres plantes, généralement non comestibles (par exemple : oignon de tulipe).\r\n" + 
						"\r\n" + 
						"L'oignon est utilisé à la fois comme légume et comme condiment.\r\n" + 
						"\r\n" + 
						"Le bulbe de l'oignon se compose de bases épaissies de feuilles s'enveloppant les unes dans les autres. De façon générale on parle d'oignon pour tous les bulbes de liliacées, comme les tulipes.\r\n" + 
						"\r\n" + 
						"L'échalote est une plante très voisine de l'oignon mais elle présente un nombre de points végétatifs par bulbe plus important. La saveur de l'échalote est également plus marquée que celle de l'oignon.",
						CathIngre.LEGUME, CathIngre.CONDIMENT
				),
				new Ingredient( "vin blanc",
						"Le vin blanc est un vin produit par la fermentation alcoolique du moût des raisins à pulpe non colorée et à pellicule blanche ou noire. Il est traité de façon à conserver une couleur jaune transparente au produit final. La grande variété des vins blancs provient de la grande quantité de cépages, des modes de vinification, mais aussi du taux de sucre résiduel.\r\n" + 
						"\r\n" + 
						"Issu de la longue histoire de la culture de la vigne par l'homme, l'existence du vin blanc date d'il y a au moins 2 500 ans. Il a accompagné le développement économique et colonisé tous les continents dont les habitants sont buveurs de vin : Europe, Amérique, Océanie, moins systématiquement en Afrique et en Asie pour des raisons climatiques et religieuses.\r\n" + 
						"\r\n" + 
						"Le vin blanc est élaboré à partir de raisins blancs (pellicule de couleur verte ou jaune) ou de raisins noirs à chair blanche. Très nombreux dans le monde, ils produisent des vins blancs dans toutes les régions où pousse la vigne. Certains cépages sont très connus comme le chardonnay B1, le sauvignon B ou le riesling B, d'autres ont une existence plus discrète, cachés derrière le nom d'un vin résultant de l'assemblage de plusieurs cépages, tels que le tokay, le xérès ou le sauternes. Le vinificateur peut aussi utiliser un cépage à pellicule colorée, à condition de veiller à ne pas colorer le moût lors de la séparation pulpe-jus. Le pinot noir N, par exemple, est couramment utilisé pour produire du champagne.\r\n" + 
						"\r\n" + 
						"Parmi les nombreux types de vin blanc, le vin blanc sec est le plus courant ; plus ou moins aromatique et acidulé, il est issu de la fermentation totale du moût. Les vins doux, moelleux ou liquoreux sont des vins élaborés avec des raisins surmûris (passerillage) ou utilisation de la pourriture noble. Pour les vins d'apéritifs, appelés vins doux naturels ou vins de liqueur, la fermentation est interrompue avant ou en début de fermentation afin de conserver la douceur du moût et d'y ajouter la force de l'alcool (10 % du volume) : on parle alors de mutage. Les vins effervescents, majoritairement blancs, sont des vins où le dioxyde de carbone (gaz carbonique) de la fermentation, maintenu dissous dans le vin, redevient gazeux à l'ouverture de la bouteille, conférant ainsi à ce vin, cette mousse caractéristique.\r\n" + 
						"\r\n" + 
						"Le vin blanc est utilisé comme boisson en apéritif, au cours du repas, en dessert ou comme boisson rafraîchissante entre les repas. Il participe aussi à l'élaboration de plats en cuisine, grâce à son acidité, ses arômes, sa capacité à attendrir les viandes et pour déglacer les sucs de cuisson. Les bienfaits pour l'organisme qui lui sont attribués sont toutefois moindres que ceux attribués au vin rouge car il est plus pauvre en composés phénoliques.",
						CathIngre.VIN
				),
				new Ingredient( "champignon de Paris",
						"Agaricus bisporus, l'agaric bispore, est une espèce de champignons basidiomycètes de la famille des Agaricaceae.\r\n" + 
						"\r\n" + 
						"Rare à l'état sauvage, ce champignon est cultivé sous le nom de champignon de Paris ou champignon de couche1. C'est le champignon le plus cultivé en champignonnière car il est simple et rapide à cultiver. Les champignons de Paris représentent environ les trois quarts de la production mondiale de champignons ; la plupart proviennent des États-Unis, de France et de Chine2.\r\n" + 
						"\r\n" + 
						"Les champignons de Paris ont des lamelles roses lorsque le champignon est jeune, puis brun-noir à noires en vieillissant. Le chapeau est rond, d'un blanc velouté qui se tache par la suite d'ocre ou de brun. Il est attaché au pied par un voile quand il est très jeune (on n'aperçoit pas ses lamelles) puis il s'ouvre en libérant un petit anneau. Il s'aplatit en vieillissant.\r\n" + 
						"\r\n" + 
						"Ce champignon pousse à l'état naturel au début de l'été ou en automne sur les sols gras, le fumier, les jardins, dans les haies de cyprès, les pâtures, les cours, toujours hors des forêts. Agaricus bisporus existe à l'état sauvage sous le Cyprès de Lambert (Cupressus macrocarpa) et d'autres conifères, sur débris végétaux, vieux mélange de paille et de crottin de cheval, dune en bordure d'océan, sous Tamaris, Prosopis, etc. Son plus proche cousin est Agaricus subfloccosus. Agaricus bisporus est souvent confondu avec le Rosé des prés (Agaricus campestris). Ce petit champignon pousse souvent en grand nombre dans les prés ou les jardins.",
						CathIngre.CHAMPIGNON
				),
				new Ingredient( "farine",
						"La farine est une poudre obtenue en broyant et en moulant des céréales ou d'autres produits agricoles alimentaires solides, souvent des graines. La farine issue de céréales contenant du gluten, comme le blé, est l'un des principaux éléments de l'alimentation de certains peuples du monde. Elle est à la base de la fabrication des pains, des pâtes, des crêpes, des pâtisseries et de plusieurs mets.\r\n" + 
						"\r\n" + 
						"L'activité de transformation de la céréale en farine s'appelle la meunerie ou minoterie. Le meunier est le transformateur qui l'exerce. Le lieu où l'on moud le blé est le moulin.\r\n" + 
						"\r\n" + 
						"Le grain de blé est constitué de trois parties : l'amande, le germe et l'enveloppe. Le germe est jeté ou mélangé à l'enveloppe pour constituer les gros sons et les sons fins, qui entrent dans la composition des pains au son ou complet. L'amande, cœur du grain de blé, est moulue pour obtenir la farine blanche. L'industrie de la meunerie y ajoute des additifs afin de modifier sa qualité.\r\n" + 
						"\r\n" + 
						"Autrefois, les farines étaient vieillies avant utilisation afin qu'elles s'oxydent1, la maturation d'une farine la rendant plus blanche naturellement. Aujourd'hui, les farines sont souvent blanchies par des agents de blanchiment ou des additifs qui accélèrent leur maturation (par exemple, le bromate de potassium2, le dioxyde de chlore2, ou l'acide ascorbique3).",
						CathIngre.FARINE
				),
				new Ingredient( "persil",
						"Le persil (Petroselinum crispum) (prononciation /pɛʁ.si/1,2,3,4 ou /pɛʁ.sil/3,4,5) est une espèce de plante herbacée de la famille des Apiacées (Ombellifères) et du genre Petroselinum. Le persil est couramment utilisé en cuisine pour ses feuilles très divisées, et en Europe centrale pour sa racine pivot. C'est également une plante médicinale.\r\n" + 
						"\r\n" + 
						"Nom scientifique : Petroselinum crispum (Mill.) Fuss (syn. : Petroselinum sativum).\r\n" + 
						"\r\n" + 
						"Noms communs : persil, persil cultivé, persil odorant, persin ; allemand : Petersilie ; anglais : parsley, persel ; italien : prezzemolo, petrosello.",
						CathIngre.HERBE
				),
				new Ingredient( "chat",
						"Chat est un terme ambigu employé en français pour désigner de nombreux félins de taille moyenne ou plus petite, appartenant à la sous-famille des félinés. Il désigne par synecdoque, l'ensemble des félins. La sous-famille des Panthérinés est parfois désigné par l'expression « grands chats ». Employé seul, il s'agit du nom vernaculaire donné au chat domestique (Felis silvestris catus). Le terme Chat sauvage définit également plusieurs espèces ou sous-espèces."		+ 
						"\r\n" + 
						"Le Chat domestique (Felis silvestris catus) est la sous-espèce issue de la domestication du Chat sauvage, mammifère carnivore de la famille des Félidés.\r\n" + 
						"\r\n" + 
						"Il est l’un des principaux animaux de compagnie et compte aujourd’hui une cinquantaine de races différentes reconnues par les instances de certification. Dans de très nombreux pays, le chat entre dans le cadre de la législation sur les carnivores domestiques à l’instar du chien et du furet. Essentiellement territorial, le chat est un prédateur de petites proies comme les rongeurs ou les oiseaux. Les chats ont diverses vocalisations dont les ronronnements, les miaulements, les feulements ou les grognements, bien qu’ils communiquent principalement par des positions faciales et corporelles et des phéromones.\r\n" + 
						"\r\n" + 
						"Selon les résultats de travaux menés en 2006 et 20071, le chat domestique est une sous-espèce du chat sauvage (Felis silvestris) issue d’ancêtres appartenant à la sous-espèce du chat sauvage d’Afrique (Felis silvestris lybica). Les premières domestications auraient eu lieu il y a 8 000 à 10 000 ans au Néolithique dans le Croissant fertile, époque correspondant au début de la culture de céréales et à l’engrangement de réserves susceptibles d’être attaquées par des rongeurs, le chat devenant alors pour l’Homme un auxiliaire utile se prêtant à la domestication.\r\n" + 
						"\r\n" + 
						"Tout d’abord vénéré par les Égyptiens, il fut diabolisé en Europe au Moyen Âge et ne retrouva ses lettres de noblesse qu’au xviiie siècle. En Asie, le chat reste synonyme de chance, de richesse ou de longévité. Ce félin a laissé son empreinte dans la culture populaire et artistique, tant au travers d’expressions populaires que de représentations diverses au sein de la littérature, de la peinture ou encore de la musique.",
						CathIngre.VIANDE)
				);
				
		System.out.println(ingredientRep);
		
		for(Ingredient i : ingredients) {
			ingredientRep.save(i);
		}
				
		return ingredients;		
	}
	
	public List<Recette> saveRecettePersonalise(){
		List<Recette> recettes = new ArrayList<>();
		/*
		 * Recette de la Soupe au Lardon
		 */
		Recette soupeAuLardon = new Recette();
		
		soupeAuLardon.setDateCreation(LocalDate.now());
		soupeAuLardon.setDateDerniereEdition(LocalDate.now());
		soupeAuLardon.setNom("Soupe au Lardon");
		soupeAuLardon.setTempsPreparation(50);
		soupeAuLardon.setTempsCuisson(80);
		soupeAuLardon.setDescription("Une soupe bien grasse et bien dégueulasse, que je déconseille fortement de manger.");
		
		soupeAuLardon.setListeEtapes(Stream.of(
				new EtapeRecette(1,"Portez à ébulition l'eau dans une casserole"),
				new EtapeRecette(2,"Ajoutez les lardons"), 
				new EtapeRecette(3,"Utilisez un mixeur pour broyer les lardons"),
				new EtapeRecette(4,"Laissez mijoter"),
				new EtapeRecette(5,"Voilà c'est prêt !")
				).collect(Collectors.toSet()));
		
		//Enregistrement de la recette dans la bdd
		soupeAuLardon = recetteRep.save(soupeAuLardon);
		
		/*
		 * Ajout de l'IngredientRecette Corespondant
		 */
		IngredientRecette ingrediantsoupeAuLardon = new IngredientRecette();
		ingrediantsoupeAuLardon.setIngredient(new Ingredient(1));
		ingrediantsoupeAuLardon.setRecette(soupeAuLardon);
		ingrediantsoupeAuLardon.setText("g de lardon");
		ingrediantsoupeAuLardon.setQuantite(500);
		//Enregistrement de l'ingredientRecette dans la bdd
		ingrediantsoupeAuLardon = ingredientRecetteRep.save(ingrediantsoupeAuLardon);
		
		/* Ajout du resultat dans la liste to return en fin de methode		 */		
		recettes.add(recetteRep.findById(soupeAuLardon.getId()).get());
		
		/*
		 * Le chat à la moutarde
		 */
		
		Recette chatALaMoutarde = new Recette();
		
		chatALaMoutarde.setDateCreation(LocalDate.now());
		chatALaMoutarde.setDateDerniereEdition(LocalDate.now());
		chatALaMoutarde.setNom("Le chat à la moutarde");
		chatALaMoutarde.setTempsPreparation(15);
		chatALaMoutarde.setTempsCuisson(60);
		chatALaMoutarde.setDescription("Fort de mes expériences culinaires et sur l'insistance de certains cordons bleus introvertis, je vous livre une recette originale qui devrait vous faire lècher les babines et friser la moustache.");
		
		chatALaMoutarde.setListeEtapes(Stream.of(
				new EtapeRecette(1,"Salez, poivrez et badigeonnez les morceaux de chat avec de la moutarde forte (n'hésitez pas sur la quantité, la moutarde perd de sa force avec la cuisson) puis farinez les morceaux."),
				new EtapeRecette(2,"Faites revenir le chat dans un peu d'huile à feu moyen, puis enlevez les morceaux du faitout."), 
				new EtapeRecette(3,"Faites suer la garniture aromatique (échalote, oignons et persil) à feu doux en décollant les sucs de cuisson du chat."), 
				new EtapeRecette(4,"Remettez les morceaux de chat (sauf le foie) dans le faitout, et mouillez avec 1/2 l de vin blanc sec. Salez et poivrez."), 
				new EtapeRecette(5,"Un quart d'heure avant la fin de la cuisson, ajoutez les champignons coupés en quartier et le foie du chat."), 
				new EtapeRecette(6,"Avant de dresser le chat, rectifiez l'assaisonnement et liez la sauce à la maïzena ou avec un beurre manié (moitié beurre, moitié farine à pétrir avant d'ajouter par petite quantité à la sauce en faisant bouillir)."), 
				new EtapeRecette(7,"Pour finir... J'utilise le même vin à table que pour la cuisson. Ce plat est encore meilleur réchauffé le lendemain. Servez-le avec des tagliatelles fraîches.")).collect(Collectors.toSet()));
		
		//Enregistrement de la recette dans la bdd
		chatALaMoutarde = recetteRep.save(chatALaMoutarde);
		
		/*
		 * Ajout de l'IngredientRecette Corespondant
		 */
		
		List<IngredientRecette> ingrediantsChatALaMoutarde = Arrays.asList(
				new IngredientRecette(0, 1d," chat découpé", chatALaMoutarde, new Ingredient(22)),
				new IngredientRecette(0, 1d," oignon", chatALaMoutarde, new Ingredient(13)),
				new IngredientRecette(0, 8d," cuillères à soupe de moutarde", chatALaMoutarde, new Ingredient(17)),
				new IngredientRecette(0, 1d," échalote", chatALaMoutarde, new Ingredient(15)),
				new IngredientRecette(0, 1d," gousse d'ail", chatALaMoutarde, new Ingredient(16)),
				new IngredientRecette(0, 1.5d," l de vin blanc", chatALaMoutarde, new Ingredient(18)),
				new IngredientRecette(0, 300d," g de champignons de Paris", chatALaMoutarde, new Ingredient(22)),
				new IngredientRecette(0, 50d," g de farine", chatALaMoutarde, new Ingredient(20)),
				new IngredientRecette(0, 0d,"sel", chatALaMoutarde, new Ingredient(9)),
				new IngredientRecette(0, 0d,"poivre", chatALaMoutarde, new Ingredient(8))
				);
		
		for(IngredientRecette ingredientRecette : ingrediantsChatALaMoutarde) {
			ingredientRecetteRep.save(ingredientRecette);			
		}
		
		/* Ajout du resultat dans la liste to return en fin de methode		 */		
		recettes.add(recetteRep.findById(chatALaMoutarde.getId()).get());
		
		return recettes;
	}
	
    public LocalDate createRandomDate(int startYear, int endYear) {
        int day = createRandomIntBetween(1, 28);
        int month = createRandomIntBetween(1, 12);
        int year = createRandomIntBetween(startYear, endYear);
        return LocalDate.of(year, month, day);
    }
    
    public int createRandomIntBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }
    	
	
}

<?php

namespace App\Controller;

use App\Entity\Product;
use App\Data\FiltreData;
use App\Form\FiltreType;
use App\Form\ProductType;
use App\Entity\Commentaire;
use App\Entity\Productlike;
use App\Form\CommentaireType;
use App\Repository\UserRepository;
use Endroid\QrCode\Builder\Builder;
use Endroid\QrCode\Writer\PngWriter;
use App\Repository\ProductRepository;
use Endroid\QrCode\Encoding\Encoding;
use App\Repository\CoffretsRepository;
use App\Repository\CatalogueRepository;
use Doctrine\Persistence\ObjectManager;
use Endroid\QrCode\Label\Font\NotoSans;
use App\Repository\CommentaireRepository;
use App\Repository\ProductlikeRepository;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\HttpFoundation\Request;
use MercurySeries\FlashyBundle\FlashyNotifier;

use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\Encoder\XmlEncoder;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\SerializerInterface;
use Endroid\QrCode\Label\Alignment\LabelAlignmentCenter;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Endroid\QrCode\RoundBlockSizeMode\RoundBlockSizeModeMargin;
use Endroid\QrCode\ErrorCorrectionLevel\ErrorCorrectionLevelHigh;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\Exception\FileException;

class ProductController extends AbstractController
{
    /**
     * @Route("/", name="product")
     * @param ProductRepository $productRepository
     * @return Response
     */
    public function index(ProductRepository $productRepository, CatalogueRepository $catalogueRepository, CoffretsRepository $coffretsRepository)
    {
        return $this->render('product/index.html.twig', [
            'products' => $productRepository->findAll(),
            'catalogues' => $catalogueRepository->findAll(),
            'data' => $coffretsRepository->findAll()
        ]);
    }
    /**
     * @Route("produit/search",name="search")
     */
    public function searchProduit(ProductRepository $repository, Request $request)
    {

        $product = '';
        $requestString = $request->get('searchValue');
        if (strlen($requestString) > 0) {
            $product = $repository->findProduitByNom($requestString);
        } else {
            $product = $repository->findAll();
        }

        $encoders = [new XmlEncoder(), new JsonEncoder()];
        $normalizers = [new ObjectNormalizer()];

        $serializer = new Serializer($normalizers, $encoders);
        $jsonContent = $serializer->serialize($product, 'json', ['ignored_attributes' => [
            'commentaires',
            'likes',
            'items'
        ]]);


        $response = new Response(json_encode($jsonContent));
        $response->headers->set('Content-Type', 'application/json; charset=utf-8');

        return $response;
    }

    /**
     * @Route("admin/produit/affiche",name="AfficheProduit")
     */
    public function AfficheProduct(ProductRepository $repository)
    {


        $product = $repository->findAll();

        return $this->render(
            'admin/product/affiche.html.twig',
            ['produit' => $product]
        );
    }


    /**
     * @Route("produit/afficheAdmin/{id}",name="AfficheProduitAdminDetaill")
     */
    public function AfficheDetaillAdmin(ProductRepository $repository, $id)
    {
        //$repo=$this->getDoctrine()->getRepository(Classroom::class);
        $product = $repository->find($id);
        return $this->render(
            'admin/product/AfficheDetaillAdmin.html.twig',
            ['produit' => $product]
        );
    }

    /**
     * @param Request $request
     * @return Response
     * @Route ("product/add" , name="ajout")
     */
    public function AjoutProduct(Request $request, FlashyNotifier $flashy)
    {
        $product = new Product();
        $form = $this->createForm(ProductType::class, $product);
        $form->add('Ajouter', SubmitType::class);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {

            $file = $form->get('image')->getData();

            $fileName = md5(uniqid()) . '.' . $file->guessExtension();
            try {
                $file->move(
                    $this->getParameter('images_directory'),
                    $fileName
                );
            } catch (FileException $e) {
            }
            $em = $this->getDoctrine()->getManager();
            $product->setImage($fileName);
            $em->persist($product);
            $em->flush();
            $flashy->success('Event created!', 'http://your-awesome-link.com');
            return $this->redirectToRoute('AfficheProduit');
        }
        return $this->render('admin/product/Add.html.twig', [
            'form' => $form->createView()
        ]);
    }
    /**
     * @Route("/Supp/{id}", name="d")
     */
    public function Delete($id, ProductRepository $repository, FlashyNotifier $flashy)
    {
        $product = $repository->find($id);
        $em = $this->getDoctrine()->getManager();
        $em->remove($product);
        $em->flush();
        $flashy->success('produit supprimé!', 'http://your-awesome-link.com');
        return $this->redirectToRoute('AfficheProduit');
    }
    /**
     * @Route("produit/Update/{id}", name="update")
     */
    function Update(ProductRepository $repository, $id, Request $request)
    {
        $product = $repository->find($id);
        $form = $this->createForm(ProductType::class, $product);
        $form->add('Update', SubmitType::class);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $file = $form->get('image')->getData();
            $fileName = md5(uniqid()) . '.' . $file->guessExtension();
            try {
                $file->move(
                    $this->getParameter('images_directory'),
                    $fileName
                );
            } catch (FileException $e) {
            }
            $em = $this->getDoctrine()->getManager();
            $product->setImage($fileName);
            $em->flush();

            $result = Builder::create()
                ->writer(new PngWriter())
                ->writerOptions([])
                ->data('TITLE: ' . $product->getTitle())
                ->encoding(new Encoding('UTF-8'))
                ->errorCorrectionLevel(new ErrorCorrectionLevelHigh())
                ->size(300)
                ->margin(10)
                ->roundBlockSizeMode(new RoundBlockSizeModeMargin())
                ->labelText('Scanner le ticket')
                ->labelFont(new NotoSans(20))
                ->labelAlignment(new LabelAlignmentCenter())
                ->build();
            header('Content-Type: ' . $result->getMimeType());

            $result->saveToFile($this->getParameter('images_directory') . '/' . $product->getId() . '.png');
            return $this->redirectToRoute('AfficheProduit');
        }
        return $this->render('admin/product/Update.html.twig', [
            'formU' => $form->createView()
        ]);
    }
    /**
     * @Route("produit/afficheClient",name="AfficheProduitClient")
     */
    public function AfficheProduitClient(ProductRepository $repository, Request $request)
    {
        $dataF = new FiltreData();
        $formF = $this->createForm(FiltreType::class, $dataF);
        $formF->handleRequest($request);
        $produit = $repository->findByFiltre($dataF);

        return $this->render('product/AfficheClient.html.twig', [
            'products' => $produit,
            'formFiltre' => $formF->createView()
        ]);
    }
    /**
     * @Route("produit/afficheClient/{id}",name="AfficheProduitClientDetaill")
     */
    public function AfficheDetaillClient(ProductRepository $repository, $id, UserRepository $repositoryU, Request $request)
    {
        //$repo=$this->getDoctrine()->getRepository(Classroom::class);

        $produit = $repository->find($id);
        $commentaire = new Commentaire();
        $form = $this->createForm(CommentaireType::class, $commentaire);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $pid = $commentaire->getProduit();
            $pidd = $pid->getId();
            $em->persist($commentaire);
            $em->flush();
            return $this->redirectToRoute('AfficheProduitClientDetaill', ['id' => $pidd]);
        }

        return $this->render(
            'product/AfficheDetaill.html.twig',
            ['product' => $produit,  'formCommentaire' => $form->createView()]
        );
    }




    /**
     * @param Product $product
     * @param ObjectManager $manager
     * @param ProductlikeRepository $productlikeRepository
     * @return Response
     * permet de liker ou unliker des produits
     * @Route("/Product/{id}/like",name="Product_like")
     */
    public function like(Product $product, ProductlikeRepository $productlikeRepository): Response
    {


        //http status code error handler

        $em = $this->getDoctrine()->getManager();


        $user = $this->getUser();
        //user not connected
        if (!$user) return $this->json([
            'code ' => 403,
            'message' => "unauthorized"
        ], 403);



        //produit deja aimé
        //supprimer un like pour un user
        if ($product->isLikedByUser($user)) {
            $like = $productlikeRepository->findOneBy([
                'product' => $product,
                'user' => $user
            ]);

            $em->remove($like);
            $em->flush();

            return $this->json([
                'code' => 200,
                'message' => 'like bien supprimer',
                'likes' => $productlikeRepository->count(['product' => $product])
            ], 200);
        }


        //créer un nouveau like
        $like = new Productlike();
        $like->setProduct($product)
            ->setUser($user);
        $em->persist($like);
        $em->flush();

        return $this->json([
            'code ' => 200,
            'message' => 'like bien ajouter',
            'likes' => $productlikeRepository->count(['product' => $product])
        ], 200);
    }


    /**
     * SUPPRESSION DE COMMENT
     * @param Product $product
     * @param CommentaireRepository $commentRepo
     * @return Response
     * @Route ("/produit/{id}/commentaire", name="commentaireS")
     */
    public function commented(Product $product, CommentaireRepository $commentRepo): Response
    {
        $em = $this->getDoctrine()->getManager();
        $user = $this->getUser();
        if (!$user) return $this->json([
            'code' => 403,
            'message' => "unauthorized"
        ], 403);
        //produit deja commenté
        //supprimer un commentaire pour un user
        if ($product->isCommentedByUser($user)) {
            $commentaire = $commentRepo->findOneBy([
                'produit' => $product,
                'user' => $user
            ]);
            $em->remove($commentaire);
            $em->flush();
        }
        return $this->json([
            'code' => 200,
            'message' => "comment BIEN SUPPRIME",
            'commentaires' => $commentRepo->count(['produit' => $product])
        ], 200);
    }


    /******************Ajouter product*****************************************/
    /**
     * @Route("/addProduct", name="add_product" , methods={"GET","POST"})
     *
     */
    public function ajouterProductAction(Request $request)
    {
        $product = new Product();
        $title = $request->query->get("title");
        $image = $request->query->get("image");
        $price = $request->query->get("price");
        $enVente = $request->query->get("enVente");
        $quantity = $request->query->get("quantity");
        $description = $request->query->get("description");
        $em = $this->getDoctrine()->getManager();
        $product->setTitle($title);
        $product->setImage($image);
        $product->setPrice($price);
        $product->setEnVente($enVente);
        $product->setQuantity($quantity);
        $product->setDescription($description);
        $em->persist($product);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($product);
        return new JsonResponse($formatted);
    }

    /******************Supprimer product*****************************************/

    /**
     * @Route("/deleteProduct", name="delete_product")
     *
     */

    public function deleteProductAction(Request $request)
    {
        $id = $request->get("id");

        $em = $this->getDoctrine()->getManager();
        $product = $em->getRepository(Product::class)->find($id);
        if ($product != null) {
            $em->remove($product);
            $em->flush();

            $serialize = new Serializer([new ObjectNormalizer()]);
            $formatted = $serialize->normalize("produIt a ete supprimee avec success.");
            return new JsonResponse($formatted);
        }
        return new JsonResponse("id product invalide.");
    }

    /******************Modifier product*****************************************/
    /**
     * @Route("/updateProduct", name="update_product" )
     *
     */
    public function modifierProductAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $product = $this->getDoctrine()->getManager()
            ->getRepository(Product::class)
            ->find($request->get("id"));
        $product->setTitle($request->get("title"));
        $product->setImage($request->get("image"));
        $product->setPrice($request->get("price"));
        $product->setEnVente($request->get("enVente"));
        $product->setQuantity($request->get("quantity"));
        $product->setDescription($request->get("description"));

        $em->persist($product);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($product);
        return new JsonResponse("product a ete modifiee avec success.");
    }



    /******************affichage Product*****************************************/

    /**
     * @Route("/displayProducts", name="display_products")
     */
    public function allProdAction(SerializerInterface $serializer)
    {

        $product = $this->getDoctrine()->getManager()->getRepository(Product::class)->findAll();
        $serializer = new Serializer([new ObjectNormalizer()]);
        //$formatted = $serializer->normalize($product);
        // return new JsonResponse($formatted);
        return $this->json($product, Response::HTTP_OK, [], [
            ObjectNormalizer::IGNORED_ATTRIBUTES => [
                'commentaires',
                'likes',
                'items',
                'categorie',
                'ProductCoffrets'
            ],
            ObjectNormalizer::CIRCULAR_REFERENCE_HANDLER => function ($object) {
                return $object->getId();
            }
        ]);
    }


    /******************Detail Product*****************************************/

    /**
     * @Route("/detailProduct", name="detail_product", methods={"GET"})
     *
     */
    //Detail Reclamation
    public function detailProductAction(Request $request)
    {
        $id = $request->get("id");

        $em = $this->getDoctrine()->getManager();
        $product = $this->getDoctrine()->getManager()->getRepository(Product::class)->find($id);
        $encoder = new JsonEncoder();
        $normalizer = new ObjectNormalizer();
        //$normalizer->setCircularReferenceHandler(function ($object) {
        //    return $object->getDescription();
        //   });
        $serializer = new Serializer([$normalizer], [$encoder]);
        $formatted = $serializer->normalize($product);
        return new JsonResponse($formatted);
    }

    /******************affichage Filtred Product*****************************************/

    /**
     * @Route("/displayProductsFiltred", name="display_productsfiltred")
     */
    public function filtredProdAction(SerializerInterface $serializer, Request $request, ProductRepository $REPO)
    {

        //$REPO = $this->getDoctrine()->getManager()->getRepository(Product::class);
        $dataF = new FiltreData();
        $envente = $request->query->get("enVente");
        $priceMin = $request->query->get("min");
        $priceMax = $request->query->get("max");
        if ($priceMin) {
            $dataF->setMin($priceMin);
        }
        if ($priceMax) {
            $dataF->setMax($priceMax);
        }
        if ($envente) {
            $dataF->setEnVente($envente);
        }
        $product = $REPO->findByFiltre($dataF);
        $serializer = new Serializer([new ObjectNormalizer()]);
        //$formatted = $serializer->normalize($product);
        // return new JsonResponse($formatted);
        return $this->json($product, Response::HTTP_OK, [], [
            ObjectNormalizer::IGNORED_ATTRIBUTES => [
                'commentaires',
                'likes',
                'items',
                'categorie',
                'ProductCoffrets'
            ],
            ObjectNormalizer::CIRCULAR_REFERENCE_HANDLER => function ($object) {
                return $object->getId();
            }
        ]);
    }

    /******************Search Product*****************************************/
    /**
     * @Route("/searchA",name="searchA")
     */
    public function searchProduitAction(ProductRepository $repository, Request $request)
    {


        $requestString = $request->get('searchValue');
        if (strlen($requestString) > 0) {
            $product = $repository->findProduitByNom($requestString);
        } else {
            $product = $repository->findAll();
        }

        return $this->json($product, Response::HTTP_OK, [], [
            ObjectNormalizer::IGNORED_ATTRIBUTES => [
                'commentaires',
                'likes',
                'items',
                'categorie',
                'ProductCoffrets'
            ],
            ObjectNormalizer::CIRCULAR_REFERENCE_HANDLER => function ($object) {
                return $object->getId();
            }
        ]);
    }
}

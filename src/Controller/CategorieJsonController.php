<?php

namespace App\Controller;

use App\Entity\Catalogue;
use App\Entity\Categorie;
use App\Entity\CategorieFavories;
use App\Form\CategorieType;
use App\Repository\CategorieFavoriesRepository;
use App\Repository\CategorieRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Encoder\XmlEncoder;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\SerializerInterface;

class CategorieJsonController extends AbstractController
{


    /**
     * @Route("/categorie", name="categorie")
     */
    public function index(): Response
    {
        return $this->render('categorie/index.html.twig', [
            'controller_name' => 'CategorieController',
        ]);
    }

    /**
     * @Route("/categorieAll", name="categorieAll")
     */
    public function afficheClientCategorie(SerializerInterface $serializer)
    {
        $product = $this->getDoctrine()->getManager()->getRepository(Categorie::class)->findAll();
        $serializer = new Serializer([new ObjectNormalizer()]);
        //$formatted = $serializer->normalize($product);
        // return new JsonResponse($formatted);
        return $this->json($product, Response::HTTP_OK, [], [
            ObjectNormalizer::IGNORED_ATTRIBUTES => [

                'product'
            ],
            ObjectNormalizer::CIRCULAR_REFERENCE_HANDLER => function ($object) {
                return $object->getId();
            }
        ]);
    }



    /**
     * @Route("/addCategorie", name="add_categorie" , methods={"GET","POST"})
     *
     */
    public function ajouterCategorieAction(Request $request)
    {
        $id = $request->query->get("catalogue");
        $categorie = new categorie();

        $cata = $this->getDoctrine()->getManager()->getRepository(Catalogue::class)->find($id);
        //$catalogue_id = $request->query->get("catalogue_id");
        $nom = $request->query->get("nom");
        $description = $request->query->get("description");
        $image = $request->query->get("image");

        $em = $this->getDoctrine()->getManager();
        $categorie->setCatalogue($cata);
        $categorie->setNom($nom);

        $categorie->setDescription($description);
        $categorie->setImage($image);

        $em->persist($categorie);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($categorie);
        return new JsonResponse($formatted);
    }

    /**
     * @Route("/deleteCategorie", name="deleteCategorie")
     *
     */

    public function deleteCategorieAction(Request $request)
    {
        $id = $request->get("id");

        $em = $this->getDoctrine()->getManager();
        $catalogue = $em->getRepository(Categorie::class)->find($id);
        if ($catalogue != null) {
            $em->remove($catalogue);
            $em->flush();

            $serialize = new Serializer([new ObjectNormalizer()]);
            $formatted = $serialize->normalize("categorie a ete supprimee avec success.");
            return new JsonResponse($formatted);
        }
        return new JsonResponse("id categorie invalide.");
    }
    /**
     * @Route("/updateCategorie", name="updateCategorie" )
     *
     */
    public function modifierCategorieAction(Request $request)
    {

        $em = $this->getDoctrine()->getManager();
        $catalogue = $this->getDoctrine()->getManager()
            ->getRepository(Categorie::class)
            ->find($request->get("id"));

        $id = $request->get("catalogue");
        $catalog = $this->getDoctrine()->getManager()->getRepository(Catalogue::class)->find($id);


        $catalogue->setCatalogue($catalog);
        $catalogue->setNom($request->get("nom"));
        $catalogue->setDescription($request->get("description"));
        $catalogue->setImage($request->get("image"));


        $em->persist($catalogue);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($catalogue);
        return new JsonResponse("Categorie a ete modifiee avec success.");
    }
}

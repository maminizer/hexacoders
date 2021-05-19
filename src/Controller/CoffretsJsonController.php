<?php

namespace App\Controller;

use App\Entity\Coffrets;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\SerializerInterface;

class CoffretsJsonController extends AbstractController
{
    /**
     * @Route("/coffrets", name="coffrets")
     */
    public function index(): Response
    {
        return $this->render('coffrets/index.html.twig', [
            'controller_name' => 'CoffretsController',
        ]);
    }




    /**
     * @Route("/coffretsAll", name="coffretsAll")
     */
    public function afficheClientCategorie(SerializerInterface $serializer)
    {
        $product = $this->getDoctrine()->getManager()->getRepository(Coffrets::class)->findAll();
        $serializer = new Serializer([new ObjectNormalizer()]);
        //$formatted = $serializer->normalize($product);
        // return new JsonResponse($formatted);
        return $this->json($product, Response::HTTP_OK, [], [
            ObjectNormalizer::IGNORED_ATTRIBUTES => [

                'productCoffrets'
            ],
            ObjectNormalizer::CIRCULAR_REFERENCE_HANDLER => function ($object) {
                return $object->getId();
            }
        ]);
    }

    /**
     * @Route("/addCoffrets", name="/addCoffrets" , methods={"GET","POST"})
     *
     */

    public function ajouterCategorieAction(Request $request)
    {
        $coffrets = new Coffrets();

        $nom = $request->query->get("nom");

        $image = $request->query->get("image");
        $description = $request->query->get("description");
        $prix = $request->query->get("prix");
        $offre = $request->query->get("offre");


        $em = $this->getDoctrine()->getManager();

        $coffrets->setNom($nom);
        $coffrets->setDescription($description);
        $coffrets->setImage($image);
        $coffrets->setPrix($prix);
        $coffrets->setOffre($offre);

        $em->persist($coffrets);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($coffrets);
        return new JsonResponse($formatted);
    }



    /**
     * @Route("/deleteCoffrets", name="deleteCoffrets")
     *
     */
    public function deleteCoffretAction(Request $request)
    {
        $id = $request->get("id");

        $em = $this->getDoctrine()->getManager();
        $catalogue = $em->getRepository(Coffrets::class)->find($id);
        if ($catalogue != null) {
            $em->remove($catalogue);
            $em->flush();

            $serialize = new Serializer([new ObjectNormalizer()]);
            $formatted = $serialize->normalize("coffrets a ete supprimee avec success.");
            return new JsonResponse($formatted);
        }
        return new JsonResponse("id coffrets invalide.");
    }
}

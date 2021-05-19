<?php

namespace App\Controller;

use App\Entity\Commande;
use Doctrine\ORM\EntityManager;
use App\Repository\CommandeRepository;
use App\Repository\ProductRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;

use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;

class CommandeJsonController extends AbstractController
{
    /**
     * @Route("/user/json", name="user_json")
     */
    public function index(): Response
    {
        return $this->render('user_json/index.html.twig', [
            'controller_name' => 'UserJsonController',
        ]);
    }

    /**
     * @Route("/api/v1/commandeshow/{id}" , name="commandeshow")
     */
    public function showCommande(CommandeRepository $commandeRepository, SerializerInterface $serializerInterface, $id)
    {
        $commande = $commandeRepository->find($id);
        $json = $serializerInterface->serialize($commande, 'json', ['groups' => 'commandeG']);
        return new Response(json_encode($json));
    }

    /**
     * @Route("/api/v1/CommandeAll" , name="CommandeAll")
     */
    public function listCommande(CommandeRepository $commandeRepository, SerializerInterface $serializerInterface,NormalizerInterface $normalizer)
    {
        $repo = $commandeRepository->findAll();
        $json = $normalizer->normalize($repo, 'json', ['groups' => 'commandeG']);


        return new Response(json_encode($json));
    }

    /**
     * @Route("/api/v1/commandeadd" , name="commandeadd")
     */
    public function addCommande(Request $request, SerializerInterface $serializerInterface, EntityManagerInterface $em)
    {
        $content = $request->getContent();
        $data = $serializerInterface->deserialize($content, Commande::class, 'json');
        $em->persist($data);
        $em->flush();

        return new Response('commande added Successfully');
    }

    /**
     *@Route("/api/v1/commandedelete/{id}" , name ="commandedelete")
     */
    public function deleteCommande(SerializerInterface $serializerInterface, CommandeRepository $commandeRepository, EntityManagerInterface $em, $id)
    {
        $commande = $commandeRepository->find($id);
        $em->remove($commande);
        $em->flush();

        return new Response('commande deleted of id: ' . $id );
    }


    /**
     * @Route("/api/v1/commandeAdd/{name_product}/{id_user}/{quantity}" , name="commandeAdd")
     */
    public function newCommande(Request $request, SerializerInterface $serializerInterface, EntityManagerInterface $em, $name_product,$id_user,$quantity , ProductRepository $productRepository)
    {

        $product = $productRepository->findOneBy([
            "title" => $name_product
        ]);

        $date = new \DateTime("now");
        $dates = $date->format('Y-m-d H:i');

        $commande = new Commande();
        $commande->setIdProduct($product->getId());
        $commande->setIdUser($id_user);
        $commande->setQuantity($quantity);
        $commande->setValidated(1);
        $commande->setCreatedAt($dates);

        $em->persist($commande);
        $em->flush();

        return new Response("Commande Edited");
    }

      /**
     * @Route("/api/v1/CommandeUser/{id}" , name="CommandeUser")
     */
    public function CommandeUser($id,CommandeRepository $commandeRepository, SerializerInterface $serializerInterface,NormalizerInterface $normalizer)
    {
        $repo = $commandeRepository->findBy([
            "idUser" => $id
        ]);
        $json = $normalizer->normalize($repo, 'json', ['groups' => 'commandeG']);


        return new Response(json_encode($json));
    }

}

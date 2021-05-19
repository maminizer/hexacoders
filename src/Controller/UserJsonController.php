<?php

namespace App\Controller;

use App\Entity\User;
use Doctrine\ORM\EntityManager;
use App\Repository\UserRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Security\Core\Encoder\EncoderFactory;
use Symfony\Component\Security\Core\Encoder\EncoderFactoryInterface;
use Symfony\Component\Security\Core\Encoder\MessageDigestPasswordEncoder;



class UserJsonController extends AbstractController
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
     * @Route("/api/v1/userall/{id}" , name="usershow")
     */
    public function showUser(UserRepository $userRepository, SerializerInterface $serializerInterface, $id)
    {
        $user = $userRepository->find($id);
        $json = $serializerInterface->serialize($user, 'json', ['groups' => 'user']);
        return new Response(json_encode($json));
    }

    /**
     * @Route("/api/v1/userall" , name="userall")
     */
    public function listUser(UserRepository $userRepository, SerializerInterface $serializerInterface, NormalizerInterface $normalizer): Response
    {
        $repo = $userRepository->findAll();
        $jsonContent = $normalizer->normalize($repo, 'json', ['groups' => 'user']);


        return new Response(json_encode($jsonContent));
    }

    /**
     * @Route("/api/v1/useradd" , name="useradd")
     */
    public function addUser(Request $request, SerializerInterface $serializerInterface, EntityManagerInterface $em)
    {
        $content = $request->getContent();
        dd($request);
        $data = $serializerInterface->deserialize($content, User::class, 'json');
        $em->persist($data);
        $em->flush();

        return new Response("user added");
    }

    /**
     *@Route("/api/v1/userdelete/{id}" , name ="userdelete")
     */
    public function deleteUser(SerializerInterface $serializerInterface, UserRepository $userRepository, EntityManagerInterface $em, $id)
    {
        $user = $userRepository->find($id);
        $em->remove($user);
        $em->flush();

        return new Response('User deleted of id: ' . $id . ' and email : ' . $user->getEmail());
    }

    /**
     * @Route("/api/v1/useredit/{id}" , name="useredit")
     */
    public function editUser(Request $request, SerializerInterface $serializerInterface, UserRepository $userRepository, EntityManagerInterface $em, $id)
    {
        $content = $request->getContent();
        $data = $serializerInterface->deserialize($content, User::class, 'json');
        $user = $userRepository->find($id);

        $user->setEmail($data->getEmail());
        $user->setPassword($data->getPassword());
        $user->setFirstname($data->getFirstname());
        $user->setLastname($data->getLastname());
        $user->setNbrTel($data->getNbrTel());

        $em->persist($user);
        $em->flush();

        return new Response('user updated successfully');
    }

    /**
     * @Route("/api/v1/newuser/{email}/{password}/{firstname}/{lastname}/{nbrTel}" , name="usernew")
     */
    public function newUser(Request $request, SerializerInterface $serializerInterface, EntityManagerInterface $em, $email, $firstname, $lastname, $password, $nbrTel, EncoderFactoryInterface $encoderFactory)
    {
        $user = new User();
        $encoder = $encoderFactory->getEncoder($user);
        $encoded = $encoder->encodePassword($password, $user->getSalt());
        $user->setEmail($email);
        $user->setFirstname($firstname);
        $user->setLastname($lastname);
        $user->setPassword($encoded);
        $user->setNbrTel($nbrTel);
        $user->setIsVerified(1);
        $user->setRoles(["ROLE_USER"]);
        $em->persist($user);
        $em->flush();

        return new Response("user added");
    }


    /**
     * @Route("/loginMobile/{username}/{password}", name="login1")
     */
    public function login(Request $request, $username, $password, UserRepository $userRepository, EncoderFactoryInterface $encoderFactory): Response
    {
        // $user_manager = $this->get('fos_user.user_manager');
        //$factory = $this->get('security.encoder_factory');

        $data = [
            'type' => 'validation error',
            'title' => 'There was a validation error',
            'errors' => 'username or password invalide'
        ];
        $response = new JsonResponse($data, 400);

        $user = $userRepository->findOneBy([
            'email' => $username,
        ]);

        if (!$user)
            return $response;


        $encoder = $encoderFactory->getEncoder($user);

        //$bool = ($encoder->isPasswordValid($user->getPassword(), $password, $user->getSalt())) ? "true" : "false";
        $bool = ($encoder->isPasswordValid($user->getPassword(), $password, $user->getSalt())) ? "true" : "false";
        if ($bool == "true") {
            $role = $user->getRoles();

            $data = array(
                'type' => 'Login succeed',
                'id' => $user->getId(),
                'firstname' => $user->getFirstname(),
                'lastname' => $user->getLastname(),
                'email' => $user->getEmail(),
                'nbrtel' => $user->getNbrTel(),
                'Password' => $user->getPassword(),
                'roles' => $user->getRoles()
            );
            $response = new JsonResponse($data, 200);
            return $response;
        } else {
            return $response;
        }
    }
    /**
     * @Route("/fetchuser/{id}", name="fetchuser")
     */

    public function fetchuser($id, UserRepository $userRepository)
    {
        $user = $userRepository->find($id);
        $data = array(
            'type' => 'Login succeed',
            'id' => $user->getId(),
            'firstname' => $user->getFirstname(),
            'lastname' => $user->getLastname(),
            'email' => $user->getEmail(),
            'nbrtel' => $user->getNbrTel(),
            'Password' => $user->getPassword(),
            'roles' => $user->getRoles()
        );
        $response = new JsonResponse($data, 200);
        return $response;
    }

    /**
     * @Route("/api/v1/updateuser/{id}/{email}/{password}/{firstname}/{lastname}/{nbrTel}" , name="updateuser")
     */
    public function updateuser(Request $request, SerializerInterface $serializerInterface, EntityManagerInterface $em, $email, $firstname, $lastname, $password, $nbrTel, $id, EncoderFactoryInterface $encoderFactory, UserRepository $userRepository)
    {
        $user = $userRepository->find($id);
        $encoder = $encoderFactory->getEncoder($user);
        $encoded = $encoder->encodePassword($password, $user->getSalt());
        $user->setEmail($email);
        $user->setFirstname($firstname);
        $user->setLastname($lastname);
        $user->setPassword($encoded);
        $user->setNbrTel($nbrTel);
        $user->setIsVerified(1);
        $user->setRoles(["ROLE_USER"]);
        $em->persist($user);
        $em->flush();

        return new Response("user added");
    }
}

<?php

namespace App\DataFixtures;

use Doctrine\Bundle\FixturesBundle\Fixture;
use Doctrine\Persistence\ObjectManager;
use App\Entity\Article;

class ArticleFixtures extends Fixture
{
    public function load(ObjectManager $manager)
    {
        for($i=1; $i <=10 ; $i++){
            $article = new Article();
            $article->SetTitle("article $i")
                    ->SetContent("<p>this is the content de $i </p>")
                    ->SetImage("http://placehold.it/350x150")
                    ->SetCreatedAt( new \DateTime());
            $manager->persist($article);
           
        }
        $manager->flush();
    }
}

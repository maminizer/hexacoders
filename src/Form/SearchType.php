<?php

namespace App\Form;

use App\Data\SearchData;
use App\Entity\Categorie;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\NumberType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\TextType;

class SearchType extends AbstractType
{

    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder->add('q',TextType::class , [
            'label' => false,
            'required' =>false,
            'attr'=> [
                'placeholder' => 'Rechercher',
            ]
        ])
        ->add('categories', EntityType::class, [
            'label' => false,
            'required'=> false,
            'class' => Categorie::class,
            'choice_label' => 'nom',
            'attr' => [
                'placeholder' => 'Catégorie'
            ]
            
        ])
        ->add('min' , NumberType::class, [
            'label' => false,
            'required' =>false,
            'attr' => [
                'placeholder' => 'Prix minimum'
            ]
        ])
        ->add('max' , NumberType::class, [
            'label' => false,
            'required' =>false,
            'attr' => [
                'placeholder' => 'Prix maximun'
            ]
        ]);
    }

    public function configureOptions (OptionsResolver $resolver){

        $resolver->setDefaults([
             'data_class' => SearchData::class,
             'method' => 'GEt',
             'csrf_protection' => false,

        ]
        );
    }

    public function getBlockPrefix()
    {
        return '';
    }
}
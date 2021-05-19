<?php

namespace App\Form;

use App\Entity\Reclamations;
use Doctrine\DBAL\Types\DateTimeType;
use Doctrine\DBAL\Types\TextType;
use Doctrine\DBAL\Types\choiceType;
use phpDocumentor\Reflection\Types\Integer;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\DateTime;

class ReclamationType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('Nom' )
            ->add('Email')
            ->add('Numero_telephone')
            ->add('Type_reclamation' )
            ->add('Priorite' , \Symfony\Component\Form\Extension\Core\Type\ChoiceType::class , [
                'label' => 'Type',
                'choices'  => [
                    'Hight' => 'Hight',
                    'Meduim'=>'Meduim',
                    'Low'=>'Low',

                ]
            ])
            ->add('Date')
            ->add('Message')



        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Reclamations::class,
        ]);
    }
}

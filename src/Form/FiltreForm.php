<?php


namespace App\Form;


use App\Data\FiltreData;
use Symfony\Component\Form\Extension\Core\Type\CheckboxType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\NumberType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class FiltreForm extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('EnVente', CheckboxType::class, [
                'label' => '    ',
                'required' => false,
            ])
            ->add('min', NumberType::class,[
                'label' => false,
                'required'=> false,
                'attr' => [
                    'placeholder'=>'min'
                ]
            ])
            ->add('max', NumberType::class,[
                'label' => false,
                'required'=> false,
                'attr' => [
                    'placeholder'=>'max'
                ]
            ])
        ;
    }
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => FiltreData::class,
            'method' => 'GET',
            'csrf_protection' => false
        ]);
    }
    public function getBlockPrefix()
    {
        return '';
    }

}
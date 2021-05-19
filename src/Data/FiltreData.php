<?php


namespace App\Data;


class FiltreData
{

    /**
     * @var null|integer
     */
    public $max;

    /**
     * @var null|integer
     */
    public $min;

    /**
     * @var boolean
     */
    public $enVente;

    /**
     * @param int|null $min
     */
    public function setMin(?int $min): void
    {
        $this->min = $min;
    }

    /**
     * @param int|null $max
     */
    public function setMax(?int $max): void
    {
        $this->max = $max;
    }

    /**
     * @param bool $enVente
     */
    public function setEnVente(bool $enVente): void
    {
        $this->enVente = $enVente;
    }


}
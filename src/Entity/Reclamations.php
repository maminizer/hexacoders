<?php

namespace App\Entity;

use App\Repository\ReclamationsRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use PhpParser\Node\Scalar\String_;

/**
 * @ORM\Entity(repositoryClass=ReclamationsRepository::class )
 */
class Reclamations
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;


    /**
     * @ORM\Column(type="string", length=255 )
     */
    private $Type_reclamation;

    /**
     * @ORM\Column(type="integer")
     */
    private $Numero_telephone;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $Message;

    /**
     * @ORM\Column(type="date")
     */
    private $Date;

    /**
     * @ORM\Column(type="integer" , options={"default" : 0}  )
     */
    private $Etat;


    /**
     * @ORM\Column(type="string", length=255)
     */
    private $Priorite;


    /**
     * @ORM\Column(type="string", length=255)
     */
    private $Email;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $Nom;


    /**
     * @ORM\ManyToOne(targetEntity=ReclamationAdmin::class, inversedBy="reclamations")
     */
    private $reclamationAdmin;

    public function __construct()
    {
        $this->reclamationAdmins = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }


    public function getTypeReclamation(): ?String
    {
        return $this->Type_reclamation;
    }

    public function setTypeReclamation(string $Type_reclamation): self
    {
        $this->Type_reclamation = $Type_reclamation;

        return $this;
    }

    public function getNumeroTelephone(): ?int
    {
        return $this->Numero_telephone;
    }

    public function setNumeroTelephone(int $Numero_telephone): self
    {
        $this->Numero_telephone = $Numero_telephone;

        return $this;
    }

    public function getMessage(): ?string
    {
        return $this->Message;
    }

    public function setMessage(string $Message): self
    {
        $this->Message = $Message;

        return $this;
    }

    public function getDate(): ?\DateTimeInterface
    {
        return $this->Date;
    }

    public function setDate(\DateTimeInterface $Date): self
    {
        $this->Date = $Date;

        return $this;
    }

    public function getEtat(): ?int
    {
        return $this->Etat;
    }

    public function setEtat(int $Etat): self
    {
        $this->Etat = $Etat;

        return $this;
    }


    public function getPriorite(): ?string
    {
        return $this->Priorite;
    }

    public function setPriorite(string $Priorite): self
    {
        $this->Priorite = $Priorite;

        return $this;
    }



    public function getEmail(): ?string
    {
        return $this->Email;
    }

    public function setEmail(string $Email): self
    {
        $this->Email = $Email;

        return $this;
    }

    public function getNom(): ?string
    {
        return $this->Nom;
    }

    public function setNom(string $Nom): self
    {
        $this->Nom = $Nom;

        return $this;
    }

    /**
     * @return Collection|ReclamationAdmin[]
     */
    public function getReclamationAdmins(): Collection
    {
        return $this->reclamationAdmins;
    }

    public function addReclamationAdmin(ReclamationAdmin $reclamationAdmin): self
    {
        if (!$this->reclamationAdmins->contains($reclamationAdmin)) {
            $this->reclamationAdmins[] = $reclamationAdmin;
            $reclamationAdmin->addReclamation($this);
        }

        return $this;
    }

    public function removeReclamationAdmin(ReclamationAdmin $reclamationAdmin): self
    {
        if ($this->reclamationAdmins->removeElement($reclamationAdmin)) {
            $reclamationAdmin->removeReclamation($this);
        }

        return $this;
    }

    public function getReclamationAdmin(): ?ReclamationAdmin
    {
        return $this->reclamationAdmin;
    }

    public function setReclamationAdmin(?ReclamationAdmin $reclamationAdmin): self
    {
        $this->reclamationAdmin = $reclamationAdmin;

        return $this;
    }
}

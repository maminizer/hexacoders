<?php

namespace App\Entity;

use App\Repository\ReclamationAdminRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=ReclamationAdminRepository::class)
 */
class ReclamationAdmin
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $Message;

    /**
     * @ORM\Column(type="date")
     */
    private $Date;


    /**
     * @ORM\OneToMany(targetEntity=Reclamations::class, mappedBy="reclamationAdmin")
     */
    private $reclamations;

    public function __construct()
    {
        $this->reclamationAdmins = new ArrayCollection();
        $this->reclamations = new ArrayCollection();
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



    public function getEtat(): ?int
    {
        return $this->Etat;
    }



    public function getPriorite(): ?string
    {
        return $this->Priorite;
    }



    public function getEmail(): ?string
    {
        return $this->Email;
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


    public function getId(): ?int
    {
        return $this->id;
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



    /**
     * @return Collection|Reclamations[]
     */
    public function getReclamations(): Collection
    {
        return $this->reclamations;
    }

    public function addReclamation(Reclamations $reclamation): self
    {
        if (!$this->reclamations->contains($reclamation)) {
            $this->reclamations[] = $reclamation;
        }

        return $this;
    }

    public function removeReclamation(Reclamations $reclamation): self
    {
        $this->reclamations->removeElement($reclamation);

        return $this;
    }
}

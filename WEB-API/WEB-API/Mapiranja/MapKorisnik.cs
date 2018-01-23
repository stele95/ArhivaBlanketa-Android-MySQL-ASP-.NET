using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using FluentNHibernate.Mapping;
using WEB_API.Models;

namespace WEB_API.Mapiranja
{
    class MapKorisnik : ClassMap<Korisnik>
    {
        public MapKorisnik()
        {
            //Mapiranje tabele
            Table("Korisnici");

            //mapiranje primarnog kljuca
            Id(x => x.IdKorisnik, "id_korisnik").GeneratedBy.Identity();


            //mapiranje svojstava
            Map(x => x.Ime, "ime");
            Map(x => x.Prezime, "prezime");
            Map(x => x.BrIndeksa, "br_indexa");
            Map(x => x.Username, "username");
            Map(x => x.Password, "pass");
            Map(x => x.Email, "email");
            Map(x => x.Admin, "admin");
            //Map(x => x.IdFakultet, "ID_FAKULTET_FK");

            
            References(x => x.Fakultet).Column("id_fakultet_FK");

            /*HasManyToMany(x => x.Slusa)
                .Table("Slusa")
                .ParentKeyColumn("id_korisnik_FK")
                .ChildKeyColumn("id_predmet_FK")
                .Cascade.All();*/
        }


    }
}
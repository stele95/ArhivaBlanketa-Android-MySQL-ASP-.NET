using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using FluentNHibernate.Mapping;
using WEB_API.Models;

namespace WEB_API.Mapiranja
{
    class MapSlusa : ClassMap<Slusa>
    {
        public MapSlusa()
        {
            Table("Slusa");

            CompositeId(x => x.Id)
                .KeyReference(x => x.Korisnik, "id_korisnik_FK")
                .KeyReference(x => x.Predmet, "id_predmet_FK");
                

            

        }
    }
}
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WEB_API.Models;
using FluentNHibernate.Mapping;

namespace WEB_API.Mapiranja
{
    class MapFakultet : ClassMap<Fakultet>
    {
        public MapFakultet()
        {
            //Mapiranje tabele
            Table("Fakulteti");

            //mapiranje primarnog kljuca
            Id(x => x.IdFakultet, "id_fakultet").GeneratedBy.Identity();

            //mapiranje svojstava
            Map(x => x.Naziv, "naziv");
            Map(x => x.Grad, "grad");

        
        }
    }
}
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WEB_API.Models;
using FluentNHibernate.Mapping;

namespace WEB_API.Mapiranja
{
    class MapRok : ClassMap<Rok>
    {
        public MapRok()
        {
            //mapiranje tabele
            Table("Rokovi");

            //mapiranje primarnog kljuca
            Id(x => x.IdRok, "id_rok").GeneratedBy.Identity();

            //mapiranje svojstva
            Map(x => x.Naziv, "naziv");
        }
    }
}
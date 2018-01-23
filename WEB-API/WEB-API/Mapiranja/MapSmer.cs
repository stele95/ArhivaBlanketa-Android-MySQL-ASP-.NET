using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using FluentNHibernate.Mapping;
using WEB_API.Models;

namespace WEB_API.Mapiranja
{
    class MapSmer : ClassMap<Smer>
    {
        public MapSmer()
        {
            //mapiranje tabele
            Table("Smerovi");

            //mapiranje primarnog kljuca
            Id(x => x.IdSmer, "id_smer").GeneratedBy.Identity();

            //mapiranje svojstva
            Map(x => x.Naziv, "naziv");
            //Map(x => x.IdFakultet, "ID_FAKULTET_FK");

            References(x => x.Fakultet).Column("id_fakultet_FK");

            //HasMany(x => x.Predmeti).KeyColumn("id_smer_FK");
        }
    }
}
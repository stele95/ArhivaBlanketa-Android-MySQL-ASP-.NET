using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using FluentNHibernate.Mapping;
using WEB_API.Models;

namespace WEB_API.Mapiranja
{
    class MapPredmet : ClassMap<Predmet>
    {
        public MapPredmet()
        {
            //mapiranje tabele
            Table("Predmeti");

            //mapiranje primarnog kljuca
            Id(x => x.IdPredmet, "id_predmet").GeneratedBy.Identity();

            //mapiranje svojstva
            Map(x => x.Naziv, "naziv");
            //Map(x => x.IdSmer, "ID_SMER_FK");
            //Map(x => x.IdGodina, "ID_GODINA_ST_FK");

            References(x => x.Godina).Column("id_godina_st_FK");
            References(x => x.Smer).Column("id_smer_FK");

            //HasMany(x => x.Blanketi).KeyColumn("id_predmet_FK");
        }
    }
}
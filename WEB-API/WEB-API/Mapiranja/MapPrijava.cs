using FluentNHibernate.Mapping;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WEB_API.Models;

namespace WEB_API.Mapiranja
{
    public class MapPrijava : ClassMap<Prijava>
    {
        public MapPrijava()
        {
            //Mapiranje tabele
            Table("prijave");

            //mapiranje primarnog kljuca
            Id(x => x.IdPrijava, "id_prijava").GeneratedBy.Identity();



            //mapiranje svojstava
            Map(x => x.Datum, "datum");          
            Map(x => x.KomentarData, "text");



            References(x => x.Blanket).Column("blanket_FK");
            References(x => x.Dodao).Column("dodao_FK");
        }
    }
}
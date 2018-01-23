using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using FluentNHibernate.Mapping;
using WEB_API.Models;

namespace WEB_API.Mapiranja
{
    public class MapKomentar : ClassMap<Komentar>
    {
        public MapKomentar()
        {
            //Mapiranje tabele
            Table("Komentari");

            //mapiranje primarnog kljuca
            Id(x => x.IdKomentar, "id_komentar").GeneratedBy.Identity();

           

            //mapiranje svojstava
            //Map(x => x.IdBlanket, "ID_BLANKET_FK");
            Map(x => x.Datum, "datum");
            //Map(x => x.IdDodao, "ID_DODAO_FK");
            Map(x => x.PripadaBlanketu, "blanket");
            Map(x => x.KomentarData, "komentar");

            
            References(x => x.Blanket).Column("id_blanket_FK");
            References(x => x.Resenje).Column("id_resenje_fk");
            References(x => x.Dodao).Column("id_dodao_FK");

        }
    }
}

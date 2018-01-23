using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using FluentNHibernate.Mapping;
using WEB_API.Models;

namespace WEB_API.Mapiranja
{
    class MapResenje : ClassMap<Resenje>
    {
        public MapResenje()
        {
            //mapiranje tabele
            Table("Resenja");

            //mapiranje primarnog kljuca
            Id(x => x.IdResenje, "id_resenje").GeneratedBy.Identity();

            //mapiranje svojstva

            //Map(x => x.IdBlanket, "ID_BLANKET_FK");
            Map(x => x.Datum, "datum");
            //Map(x => x.IdDodao, "ID_DODAO_FK");
            Map(x => x.ImageInBytes, "image");
            Map(x => x.ThumbnailInBytes, "thumbnail");

            References(x => x.Blanket).Column("id_blanket_FK");
            
            References(x => x.Dodao).Column("id_dodao_FK");
        }
    }
}
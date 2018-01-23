using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WEB_API.Models;
using FluentNHibernate.Mapping;

namespace WEB_API.Mapiranja
{
    class MapBlanket : ClassMap<Blanket>
    {
        public MapBlanket()
        {
            //mapiranje tabele
            Table("Blanketi");

            //mapiranje primarnog kljuca
            Id(x => x.IdBlanket, "id_blanket").GeneratedBy.Identity();

            

            //mapiranje svojstva
            //Map(x => x.IdRok, "ID_ROK_FK");
            Map(x => x.Godina, "sk_godina");
            //Map(x => x.IdPredmet, "ID_PREDMET_FK");
            Map(x => x.Datum, "datum");
            //Map(x => x.IdDodao, "ID_DODAO_FK");
            //Map(x => x.IdOdobrio, "ID_ODOBRIO_FK");
            Map(x => x.Odobren, "odobren");
            Map(x => x.Pismeni, "pismeni");
            Map(x => x.Usmeni, "usmeni");
            Map(x => x.ImageInBytes, "image");
            Map(x => x.ThumbnailInBytes, "thumbnail");

            //mapiranje veze 1:N 
            References(x => x.Rok).Column("id_rok_FK");
            References(x => x.Predmet).Column("id_predmet_FK");
            References(x => x.Odobrio).Column("id_odobrio_FK");
            References(x => x.Dodao).Column("id_dodao_FK");
        }


    }
}
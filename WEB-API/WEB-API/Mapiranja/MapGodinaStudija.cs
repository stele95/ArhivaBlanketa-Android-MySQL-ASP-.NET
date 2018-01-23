using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using FluentNHibernate.Mapping;
using WEB_API.Models;

namespace WEB_API.Mapiranja
{
    class MapGodinaStudija : ClassMap<GodinaStudija>
    {
        public MapGodinaStudija()
        {
            //Mapiranje tabele
            Table("Godine_studija");

            //mapiranje primarnog kljuca
            Id(x => x.IdGodina, "id_godina_st").GeneratedBy.Identity();

            //mapiranje svojstava
            Map(x => x.Godina, "godina");

            

        }
    }
}
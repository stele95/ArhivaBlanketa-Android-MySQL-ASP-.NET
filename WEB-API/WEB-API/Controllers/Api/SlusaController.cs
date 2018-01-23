using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Http;
using WEB_API.Models;
using WEB_API.DataProviders;
using WEB_API.DTOs;
using AutoMapper;
namespace WEB_API.Controllers.Api
{
    public class SlusaController : ApiController
    {
        // GET /api/slusa
        public IEnumerable<Slusa> Get()
        {
            SlusaDataProvider provider = new SlusaDataProvider();

            IEnumerable<Slusa> s = provider.GetSlusa();

            return s;
        }
        // GET api/slusa/5
        public IEnumerable<Slusa> Get(int id)
        {
            SlusaDataProvider provider = new SlusaDataProvider();

            IEnumerable<Slusa> s = provider.GetSlusa(id);

            if (s == null)
            {
                return null;
            }
            //return Mapper.Map<Slusa, SlusaDTO>(s);
            return s;
        }

        //POST api/slusa
        public Slusa Post([FromBody]Slusa g)
        {
            SlusaDataProvider provider = new SlusaDataProvider();

            return provider.AddSlusa(g);
        }

        // DELETE api/slusa/{id}?id1={id1}
        public Slusa Delete(int id, int id1)
        {
            SlusaDataProvider provider = new SlusaDataProvider();

            return provider.RemoveSlusa(id, id1);
        }

        // PUT api/slusa/{id}?id1={id1}
        public Slusa Put(int id, int id1, [FromBody]Slusa v)
        {
            /*
             Tabela Slusa u bazi sluzi kao tabela poveznica, sto znaci
             da primary key ove tabele formiraju dva foreign kljuca i 
             zbog toga update ne moze da se radi na klasican nacin, tj.
             ne mogu da se menjaju vrednosti kolona koje cine id. Zbog toga 
             kao update ce se prvo brisati red sa zadatim id-evima pa ce se
             dodati novi red sa unetim podacima
             */

            SlusaDataProvider provider = new SlusaDataProvider();

            return provider.UpdateSlusa(id, id1, v);
        }
    }
}
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using WEB_API.Models;
using WEB_API.DataProviders;
using WEB_API.DTOs;
using AutoMapper;

namespace WEB_API.Controllers.Api
{
    public class RokController : ApiController
    {
        // GET /api/rok
        public IEnumerable<Rok> Get()
        {
            RokDataProvider provider = new RokDataProvider();

            IEnumerable<Rok> r = provider.GetRok();

            return r;
        }
        // GET api/rok/5
        public RokDTO Get(int id)
        {
            RokDataProvider provider = new RokDataProvider();

            Rok r = provider.GetRok(id);

            if (r == null)
            {
                return null;
            }
            return Mapper.Map<Rok, RokDTO>(r);
        }

        //POST api/rok
        public Rok Post([FromBody]Rok g)
        {
            RokDataProvider provider = new RokDataProvider();

            return provider.AddRok(g);
        }

        // DELETE api/rok/5
        public Rok Delete(int id)
        {
            RokDataProvider provider = new RokDataProvider();

            return provider.RemoveRok(id);
        }
        
        // PUT api/rok/5
        public Rok Put(int id, [FromBody]Rok v)
        {
            RokDataProvider provider = new RokDataProvider();

            return provider.UpdateRok(id, v);
        }
    }
}

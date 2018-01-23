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
    public class FakultetController : ApiController
    {
        // GET /api/fakultet
        public IEnumerable<Fakultet> Get()
        {
            FakultetDataProvider provider = new FakultetDataProvider();

            IEnumerable<Fakultet> s = provider.GetFakultet();

            return s;
        }
        // GET api/fakultet/5
        public FakultetDTO Get(int id)
        {
            FakultetDataProvider provider = new FakultetDataProvider();

            Fakultet s = provider.GetFakultet(id);

            if (s == null)
            {
                return null;
            }
            return Mapper.Map<Fakultet, FakultetDTO>(s);
        }

        //POST api/fakultet
        public Fakultet Post([FromBody]Fakultet g)
        {
            FakultetDataProvider provider = new FakultetDataProvider();

            return provider.AddFakultet(g);
        }

        // DELETE api/fakultet/5
        public Fakultet Delete(int id)
        {
            FakultetDataProvider provider = new FakultetDataProvider();

            return provider.RemoveFakultet(id);
        }

        // PUT api/fakultet/5
        public Fakultet Put(int id, [FromBody]Fakultet v)
        {
            FakultetDataProvider provider = new FakultetDataProvider();

            return provider.UpdateFakultet(id, v);
        }
    }
}

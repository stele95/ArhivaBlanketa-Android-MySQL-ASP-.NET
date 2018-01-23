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
    public class SmerController : ApiController
    {

        // GET /api/smer
        public IEnumerable<Smer> Get()
        {
            SmerDataProvider provider = new SmerDataProvider();

            IEnumerable<Smer> s = provider.GetSmer();

            return s;
        }
        // GET api/smer/5
        public SmerDTO Get(int id)
        {
            SmerDataProvider provider = new SmerDataProvider();

            Smer s = provider.GetSmer(id);

            if (s == null)
            {
                return null;
            }
            return Mapper.Map<Smer, SmerDTO>(s);
        }

        //POST api/smer
        public Smer Post([FromBody]Smer g)
        {
            SmerDataProvider provider = new SmerDataProvider();

            return provider.AddSmer(g);
        }

        // DELETE api/godinastudija/5
        public Smer Delete(int id)
        {
            SmerDataProvider provider = new SmerDataProvider();

            return provider.RemoveSmer(id);
        }

        // PUT api/smer/5
        public Smer Put(int id, [FromBody]Smer v)
        {
            SmerDataProvider provider = new SmerDataProvider();

            return provider.UpdateSmer(id, v);
        }

    }
}

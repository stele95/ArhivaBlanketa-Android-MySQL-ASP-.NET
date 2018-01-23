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
    public class ResenjeController : ApiController
    {

        // GET /api/Resenje
        public IEnumerable<ResenjeDTO> Get()
        {
            ResenjeDataProvider provider = new ResenjeDataProvider();

            IEnumerable<Resenje> r = provider.GetResenje();

            return Mapper.Map<IEnumerable<Resenje>, IEnumerable<ResenjeDTO>>(r);
        }
        // GET api/Resenje/5
        public Resenje Get(int id)
        {
            ResenjeDataProvider provider = new ResenjeDataProvider();

            return provider.GetResenje(id);

        }

        //POST api/Resenje
        public Resenje Post([FromBody]ResenjePostDTO g)
        {
            ResenjeDataProvider provider = new ResenjeDataProvider();

           
            return provider.AddResenje(g);
        }

        // DELETE api/Resenje/5
        public Resenje Delete(int id)
        {
            ResenjeDataProvider provider = new ResenjeDataProvider();

            return provider.RemoveResenje(id);
        }

        // PUT api/resenje/5
        public Resenje Put(int id, [FromBody]Resenje v)
        {
            ResenjeDataProvider provider = new ResenjeDataProvider();

            return provider.UpdateResenje(id, v);
        }

    }
}

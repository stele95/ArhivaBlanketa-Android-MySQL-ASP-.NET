using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using WEB_API.DataProviders;
using WEB_API.DTOs;
using WEB_API.Models;

namespace WEB_API.Controllers.Api
{
    public class PrijavaController : ApiController
    {
        // GET /api/Prijava
        public IEnumerable<Prijava> Get()
        {
            PrijavaDataProvider provider = new PrijavaDataProvider();

           return provider.GetPrijava();

            

        }
        // GET api/Prijava/5
        public Prijava Get(int id)
        {
            PrijavaDataProvider provider = new PrijavaDataProvider();

            return provider.GetPrijava(id);
                        
        }

        //POST api/Prijava
        public Prijava Post([FromBody]PrijavaDTO g)
        {
            PrijavaDataProvider provider = new PrijavaDataProvider();

            return provider.AddPrijava(g);
        }

    }
}

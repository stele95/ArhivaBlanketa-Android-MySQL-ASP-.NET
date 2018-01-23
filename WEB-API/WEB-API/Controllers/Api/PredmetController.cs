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
    public class PredmetController : ApiController
    {

        // GET /api/Predmet
        public IEnumerable<Predmet> Get()
        {
            PredmetDataProvider provider = new PredmetDataProvider();

            IEnumerable<Predmet> r = provider.GetPredmet();
            return r;
        }
        // GET api/Predmet/5
        public PredmetDTO Get(int id)
        {
            PredmetDataProvider provider = new PredmetDataProvider();

            Predmet r = provider.GetPredmet(id);

            if (r == null)
            {
                return null;
            }
            return Mapper.Map<Predmet, PredmetDTO>(r);
        }

        //POST api/Predmet
        public Predmet Post([FromBody]Predmet g)
        {
            PredmetDataProvider provider = new PredmetDataProvider();

            return provider.AddPredmet(g);
        }

        // DELETE api/Predmet/5
        public Predmet Delete(int id)
        {
            PredmetDataProvider provider = new PredmetDataProvider();

            return provider.RemovePredmet(id);
        }

        // PUT api/predmet/5
        public Predmet Put(int id, [FromBody]Predmet v)
        {
            PredmetDataProvider provider = new PredmetDataProvider();

            return provider.UpdatePredmet(id, v);
        }

    }
}

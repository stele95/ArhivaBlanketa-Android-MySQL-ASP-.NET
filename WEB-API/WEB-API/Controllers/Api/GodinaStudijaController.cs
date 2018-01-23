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
    public class GodinaStudijaController : ApiController
    {
      
        // GET /api/godinastudija
        public IEnumerable<GodinaStudija> Get()
        {
            GodinaStudijaDataProvider provider = new GodinaStudijaDataProvider();

            IEnumerable<GodinaStudija> godine = provider.GetGodinaStudija();

            return godine;
        }
        // GET api/godinastudija/5
        public GodinaStudijaDTO Get(int id)
        {
            GodinaStudijaDataProvider provider = new GodinaStudijaDataProvider();

            GodinaStudija godina = provider.GetGodinaStudija(id);

            if (godina == null)
            {
                return null;
            }
            return Mapper.Map<GodinaStudija, GodinaStudijaDTO>(godina);
        }

        //POST api/godinastudija
        public GodinaStudija Post([FromBody]GodinaStudija g)
        {
            GodinaStudijaDataProvider provider = new GodinaStudijaDataProvider();

            return provider.AddGodina(g);
        }

        // DELETE api/godinastudija/5
        public GodinaStudija Delete(int id)
        {
            GodinaStudijaDataProvider provider = new GodinaStudijaDataProvider();

            return provider.RemoveGodinaStudija(id);
        }

        // PUT api/godinastudija/5
        public GodinaStudija Put(int id, [FromBody]GodinaStudija v)
        {
            GodinaStudijaDataProvider provider = new GodinaStudijaDataProvider();

            return provider.UpdateGodinaStudija(id, v);
        }
    }
}

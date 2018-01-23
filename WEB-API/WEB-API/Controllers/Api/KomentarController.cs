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
    public class KomentarController : ApiController
    {
        // GET /api/Komentar
        public IEnumerable<Komentar> Get()
        {
            KomentarDataProvider provider = new KomentarDataProvider();

            IEnumerable<Komentar> r = provider.GetKomentar();


            return r;

        }
        // GET api/Komentar/5
        public Komentar Get(int id)
        {
            KomentarDataProvider provider = new KomentarDataProvider();

            Komentar r = provider.GetKomentar(id);

            if (r == null)
            {
                return null;
            }
            return r;
        }

        //POST api/Komentar
        public Komentar Post([FromBody]KomentarPostDTO g)
        { 
            
        
            KomentarDataProvider provider = new KomentarDataProvider();

            return provider.AddKomentar(g);
        }

        // DELETE api/Komentar/5
        public Komentar Delete(int id)
        {
            KomentarDataProvider provider = new KomentarDataProvider();

            return provider.RemoveKomentar(id);
        }

        // PUT api/komentar/5
        public Komentar Put(int id, [FromBody]Komentar v)
        {
            KomentarDataProvider provider = new KomentarDataProvider();
            return provider.UpdateKomentar(id, v);
        }
    }
}

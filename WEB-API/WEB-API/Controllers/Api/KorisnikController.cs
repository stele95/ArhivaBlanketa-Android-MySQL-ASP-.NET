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
    public class KorisnikController : ApiController
    {
        // GET /api/Korisnik
        public IEnumerable<Korisnik> Get()
        {
            KorisnikDataProvider provider = new KorisnikDataProvider();

            IEnumerable<Korisnik> r = provider.GetKorisnik();


            return r;
        }
        // GET api/Korisnik/5
        public KorisnikDTO Get(int id)
        {
            KorisnikDataProvider provider = new KorisnikDataProvider();

            Korisnik r = provider.GetKorisnik(id);

            if (r == null)
            {
                return null;
            }
            return Mapper.Map<Korisnik, KorisnikDTO>(r);
        }

        //POST api/Korisnik
        public Korisnik Post([FromBody]Korisnik g)
        {
            KorisnikDataProvider provider = new KorisnikDataProvider();

            return provider.AddKorisnik(g);
        }

        // DELETE api/Korisnik/5
        public Korisnik Delete(int id)
        {
            KorisnikDataProvider provider = new KorisnikDataProvider();

            return provider.RemoveKorisnik(id);
        }

        // PUT api/korisnik/5
        public Korisnik Put(int id, [FromBody]Korisnik v)
        {
            KorisnikDataProvider provider = new KorisnikDataProvider();

            return provider.UpdateKorisnik(id, v);
        }
    }
}

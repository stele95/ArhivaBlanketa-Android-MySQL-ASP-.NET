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
    public class LoginController : ApiController
    {
        // GET/api/login?username={username}
        public KorisnikDTO GetUsername(string username)
        {
            KorisnikDataProvider provider = new KorisnikDataProvider();

            Korisnik s = provider.GetKorisnikUsername(username);

            if (s == null)
            {
                //return new KorisnikDTO();
                return null;
            }
            return Mapper.Map<Korisnik, KorisnikDTO>(s);
        }

        // GET api/Login? email = { email }
        public KorisnikDTO GetEmail(string email)
        {
            KorisnikDataProvider provider = new KorisnikDataProvider();

            Korisnik s = provider.GetKorisnikEmail(email);

            if (s == null)
            {
                //return new KorisnikDTO();
                return null;
            }
            return Mapper.Map<Korisnik, KorisnikDTO>(s);
        }
    }
}

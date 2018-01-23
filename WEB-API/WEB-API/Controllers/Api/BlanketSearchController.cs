using AutoMapper;
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
    public class BlanketSearchController : ApiController
    {
        // GET api/BlanketSearch? predmet = {predmet}&rok={rok}&godina={godina}&pismeni={pismeni}&usmeni={usmeni}
        public BlanketDTO Get(int predmet, int rok, int godina, bool pismeni, bool usmeni)
        {
            BlanketSearch r = new BlanketSearch();
            r.IdPredmet = predmet;
            r.IdRok = rok;
            r.Godina = godina;
            r.Pismeni = pismeni;
            r.Usmeni = usmeni;

            BlanketDataProvider provider = new BlanketDataProvider();

            Blanket s = provider.NadjiBlanket(r);

            return Mapper.Map<Blanket,BlanketDTO>(s);
        }
    }
}

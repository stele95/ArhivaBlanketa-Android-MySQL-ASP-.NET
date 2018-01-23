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
using System.IO;

namespace WEB_API.Controllers.Api
{
    public class BlanketController : ApiController
    {
        // GET /api/blanket
        public IEnumerable<Blanket> Get()
        {
            BlanketDataProvider provider = new BlanketDataProvider();

            IEnumerable<Blanket> s = provider.GetBlanket();

            return s;

            //return Mapper.Map<IEnumerable<Blanket>, IEnumerable<BlanketDTO>>(s);
        }
        // GET api/blanket/5
        public Blanket Get(int id)
        {
            BlanketDataProvider provider = new BlanketDataProvider();

            Blanket s = provider.GetBlanket(id);

            if (s == null)
            {
                return null;
            }

            //s.Image=s.byteArrayToImage(s.ImageInBytes);

            return s;
            //return Mapper.Map<Blanket, BlanketDTO>(s);
        }

        //POST api/blanket
        public Blanket Post([FromBody]Blanket g)
        {
            BlanketDataProvider provider = new BlanketDataProvider();

            //Blanket b = Mapper.Map<BlanketDTO, Blanket>(g);


            //b.ImageInBytes = b.imageToByte(b.Image);
            g.Datum = DateTime.Now;
            return provider.AddBlanket(g);
        }

        // DELETE api/blanket/5
        public Blanket Delete(int id)
        {
            BlanketDataProvider provider = new BlanketDataProvider();

            return provider.RemoveBlanket(id);
        }

        // PUT api/blanket/5
        public Blanket Put(int id, [FromBody]Blanket v)
        {
            BlanketDataProvider provider = new BlanketDataProvider();
            return provider.UpdateBlanket(id, v);
        }
    }
}
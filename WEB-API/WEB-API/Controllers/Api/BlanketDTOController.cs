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
    public class BlanketDTOController : ApiController
    {
        // GET /api/blanketdto
        public IEnumerable<BlanketDTO> Get()
        {
            BlanketDataProvider provider = new BlanketDataProvider();

            IEnumerable<Blanket> s = provider.GetBlanket();

            return Mapper.Map<IEnumerable<Blanket>, IEnumerable<BlanketDTO>>(s);
        }

        // GET api/BlanketDTO/{id}
        public BlanketDTO Get(int id)
        {
            BlanketDataProvider provider = new BlanketDataProvider();

            BlanketDTO s = provider.GetBlanketDTO(id);

            if (s == null)
            {
                return null;
            }

            //s.Image=s.byteArrayToImage(s.ImageInBytes);

            return s;
            //return Mapper.Map<Blanket, BlanketDTO>(s);
        }
    }
}

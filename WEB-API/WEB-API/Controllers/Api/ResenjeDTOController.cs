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
    public class ResenjeDTOController : ApiController
    {

        // GET /api/resenjedto
        public IEnumerable<ResenjeDTO> Get()
        {
            ResenjeDataProvider provider = new ResenjeDataProvider();

            IEnumerable<Resenje> s = provider.GetResenje();

            return Mapper.Map< IEnumerable<Resenje>,IEnumerable<ResenjeDTO>>(s);
        }


        // GET api/ResenjeDTO/{id}
        public IEnumerable<ResenjeDTO> Get(int id)
        {
            ResenjeDataProvider provider = new ResenjeDataProvider();

            IEnumerable<Resenje> s = provider.GetResenjeDTO(id);

            if (s == null)
            {
                return null;
            }

            //s.Image=s.byteArrayToImage(s.ImageInBytes);

            return Mapper.Map<IEnumerable<Resenje>, IEnumerable<ResenjeDTO>>(s);
            //return Mapper.Map<Blanket, BlanketDTO>(s);
        }

    }
}

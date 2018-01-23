using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using AutoMapper;
using WEB_API.Models;
using WEB_API.DTOs;
using FluentNHibernate;

namespace WEB_API.App_Start
{
    public class MappingProfile : Profile
    {

        public MappingProfile()
        {
            Mapper.CreateMap<Blanket, BlanketDTO>();
            Mapper.CreateMap<BlanketDTO, Blanket>();

            Mapper.CreateMap<Fakultet, FakultetDTO>();
            Mapper.CreateMap<FakultetDTO, Fakultet>();

            Mapper.CreateMap<GodinaStudija, GodinaStudijaDTO>();
            Mapper.CreateMap<GodinaStudijaDTO, GodinaStudija>();

            Mapper.CreateMap<Komentar, KomentarDTO>();
            Mapper.CreateMap<KomentarDTO, Komentar>();

            Mapper.CreateMap<Korisnik, KorisnikDTO>();
            Mapper.CreateMap<KorisnikDTO, Korisnik>();

            Mapper.CreateMap<Predmet, PredmetDTO>();
            Mapper.CreateMap<PredmetDTO, Predmet>();

            Mapper.CreateMap<Resenje, ResenjeDTO>();
            Mapper.CreateMap<ResenjeDTO, Resenje>();

            Mapper.CreateMap<Rok, RokDTO>();
            Mapper.CreateMap<RokDTO, Rok>();

            Mapper.CreateMap<Slusa, SlusaDTO>();
            Mapper.CreateMap<SlusaDTO, Slusa>();

            Mapper.CreateMap<SlusaID, SlusaIDDTO>();
            Mapper.CreateMap<SlusaIDDTO, SlusaID>();

            Mapper.CreateMap<Smer, SmerDTO>();
            Mapper.CreateMap<SmerDTO, Smer>();


        }
    }
}
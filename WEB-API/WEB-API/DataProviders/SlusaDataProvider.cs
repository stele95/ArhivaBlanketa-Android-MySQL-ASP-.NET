using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using NHibernate.Linq;
using NHibernate;
using WEB_API.Models;
using WEB_API.DTOs;
using AutoMapper;

namespace WEB_API.DataProviders
{
    public class SlusaDataProvider
    {
        public IEnumerable<Slusa> GetSlusa()
        {
            ISession s = DataLayer.GetSession();

            IEnumerable<Slusa> k = s.Query<Slusa>().Select(r => r);

            return k;
        }

        public IEnumerable<Slusa> GetSlusa(int id)
        {
            ISession s = DataLayer.GetSession();

            return s.Query<Slusa>().Where(r => r.Id.Korisnik.IdKorisnik == id).Select(p => p);

        }

        public IEnumerable<SlusaDTO> GetSlusaDTO(int id)
        {
            ISession s = DataLayer.GetSession();

            IEnumerable<Slusa> d = s.Query<Slusa>().Where(r => r.Id.Korisnik.IdKorisnik == id).Select(p => p);

            if (d == null)
            { return null; }

                        
            return Mapper.Map<IEnumerable<Slusa>, IEnumerable<SlusaDTO>>(d);
        }

        public Slusa AddSlusa(Slusa sl)
        {
            try
            {
                ISession s = DataLayer.GetSession();
                s.Save(sl);
                s.Flush();
                Slusa slusa = s.Query<Slusa>()
                    .Where(r => r.Id.Korisnik.IdKorisnik == sl.Id.Korisnik.IdKorisnik)
                    .Where(r => r.Id.Predmet.IdPredmet == sl.Id.Predmet.IdPredmet)
                    .Select(p => p).FirstOrDefault();
                s.Close();
                return slusa;
            }
            catch (Exception ex)
            {
                return null;
            }
        }

        public Slusa RemoveSlusa(int id, int id1)
        {
            try
            {
                ISession s = DataLayer.GetSession();




                Slusa slusa = s.Query<Slusa>()
                    .Where(r => r.Id.Korisnik.IdKorisnik == id)
                    .Where(r => r.Id.Predmet.IdPredmet == id1)
                    .Select(p => p).SingleOrDefault();

                s.Delete(slusa);
                s.Flush();

                Slusa sl = s.Query<Slusa>()
                   .Where(r => r.Id.Korisnik.IdKorisnik == id)
                   .Where(r => r.Id.Predmet.IdPredmet == id1)
                   .Select(p => p).SingleOrDefault();
                s.Close();
                return sl;
            }
            catch (Exception ex)
            {
                return new Slusa();
            }
        }

        public Slusa UpdateSlusa(int id, int id1, Slusa sl)
        {
            try
            {
                ISession s = DataLayer.GetSession();

                RemoveSlusa(id, id1);

                s.Save(sl);
                s.Flush();

                Slusa slusa = s.Query<Slusa>()
                   .Where(r => r.Id.Korisnik.IdKorisnik == sl.Id.Korisnik.IdKorisnik)
                   .Where(r => r.Id.Predmet.IdPredmet == sl.Id.Predmet.IdPredmet)
                   .Select(p => p).SingleOrDefault();
                s.Close();
                return slusa;
            }
            catch (Exception ex)
            {
                return null;
            }
        }
    }
}
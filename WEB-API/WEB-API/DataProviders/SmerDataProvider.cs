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
    public class SmerDataProvider
    {

        public IEnumerable<Smer> GetSmer()
        {
            ISession s = DataLayer.GetSession();

            IEnumerable<Smer> smerovi = s.Query<Smer>().Select(r => r);

            return smerovi;
        }

        public Smer GetSmer(int id)
        {
            ISession s = DataLayer.GetSession();

            return s.Query<Smer>().Where(r => r.IdSmer == id).Select(p => p).FirstOrDefault();

        }

        public SmerDTO GetSmerDTO(int id)
        {
            ISession s = DataLayer.GetSession();

            Smer smer = s.Query<Smer>().Where(r => r.IdSmer == id).Select(p => p).FirstOrDefault();

            if (smer == null)
            { return null; }


            return Mapper.Map<Smer, SmerDTO>(smer);
        }

        public Smer AddSmer(Smer smer)
        {
            try
            {
                ISession s = DataLayer.GetSession();
                s.Save(smer);
                s.Flush();
                Smer sm = s.Query<Smer>().Where(c => c.Naziv == smer.Naziv).FirstOrDefault();
                s.Close();
                return sm;
            }
            catch (Exception ex)
            {
                return null;
            }
        }

        public Smer RemoveSmer(int id)
        {
            try
            {
                ISession s = DataLayer.GetSession();
                Smer r = s.Load<Smer>(id);
                s.Delete(r);
                s.Flush();
                s.Close();
                return GetSmer(id);
            }
            catch (Exception ex)
            {
                return new Smer();
            }
        }

        public Smer UpdateSmer(int id, Smer sm)
        {
            try
            {
                ISession s = DataLayer.GetSession();
                Smer r = s.Load<Smer>(id);
                r.Fakultet = sm.Fakultet;
                r.Naziv = sm.Naziv;
                s.Update(r);
                s.Flush();
                s.Close();
                return GetSmer(id);
            }
            catch (Exception ex)
            {
                return null;
            }
        }

    }
}
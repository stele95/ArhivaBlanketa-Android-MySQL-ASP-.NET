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
    public class KorisnikDataProvider
    {
        public IEnumerable<Korisnik> GetKorisnik()
        {
            ISession s = DataLayer.GetSession();

            IEnumerable<Korisnik> k = s.Query<Korisnik>().Select(p=>p);

            return k;
        }

        public Korisnik GetKorisnik(int id)
        {
            ISession s = DataLayer.GetSession();

            return s.Query<Korisnik>().Where(r => r.IdKorisnik == id).Select(p => p).FirstOrDefault();
            //return s.QueryOver<Korisnik>().Where(r => r.IdKorisnik == id).SingleOrDefault();

        }

        public KorisnikDTO GetKorisnikDTO(int id)
        {
            ISession s = DataLayer.GetSession();

            Korisnik d = s.Query<Korisnik>().Where(r => r.IdKorisnik == id).Select(p => p).FirstOrDefault();

            if (d == null)
            { return null; }


            return Mapper.Map<Korisnik, KorisnikDTO>(d);
        }

        public Korisnik GetKorisnikUsername(string username)
        {
            ISession s = DataLayer.GetSession();

            Korisnik d = s.Query<Korisnik>().Where(r => r.Username == username).Select(p => p).FirstOrDefault();

           /* if (d == null)
            { return null; }*/


            return d;
        }

        public Korisnik GetKorisnikEmail(string email)
        {
            ISession s = DataLayer.GetSession();

            Korisnik d = s.Query<Korisnik>().Where(r => r.Email == email).Select(p => p).FirstOrDefault();

            /*if (d == null)
            { return null; }*/


            return d;
        }

        public Korisnik AddKorisnik(Korisnik r)
        {
            try
            {
                ISession s = DataLayer.GetSession();
                s.Save(r);
                s.Flush();
                
                s.Close();
                return GetKorisnikEmail(r.Email);
            }
            catch (Exception ex)
            {
                return null;
            }
        }

        public Korisnik RemoveKorisnik(int id)
        {
            try
            {
                ISession s = DataLayer.GetSession();
                Korisnik r = s.Load<Korisnik>(id);
                s.Delete(r);
                s.Flush();
                s.Close();
                return GetKorisnik(id);
            }
            catch (Exception ex)
            {
                return new Korisnik();
            }
        }

        public Korisnik UpdateKorisnik(int id, Korisnik k)
        {
            try
            {
                ISession s = DataLayer.GetSession();
                Korisnik r = s.Load<Korisnik>(id);
                r.BrIndeksa = k.BrIndeksa;
                r.Email = k.Email;
                r.Fakultet = k.Fakultet;
                r.Ime = k.Ime;
                r.Prezime = k.Prezime;
                r.Username = k.Username;
                r.Password = k.Password;
                s.Update(r);
                s.Flush();
                s.Close();
                return GetKorisnik(id);

            }
            catch (Exception ex)
            {
                return null;
            }
        }
    }
}
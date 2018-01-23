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
    public class ResenjeDataProvider
    {
        public IEnumerable<Resenje> GetResenje()
        {
            ISession s = DataLayer.GetSession();

            IEnumerable<Resenje> p = s.Query<Resenje>().Select(r => r);
            foreach (Resenje b in p)
            {
                b.Image = b.byteArrayToImage(b.ImageInBytes);
            }

            return p;
        }

        public Resenje GetResenje(int id)
        {
            ISession s = DataLayer.GetSession();

            return s.Query<Resenje>().Where(r => r.IdResenje == id).Select(p => p).FirstOrDefault();

        }

        public IEnumerable<Resenje> GetResenjeDTO(int id)
        {
            ISession s = DataLayer.GetSession();

            IEnumerable<Resenje> p = s.Query<Resenje>().Where(r => r.Blanket.IdBlanket == id).Select(r => r);

            if (p == null)
            { return null; }


            return p;
        }

        public Resenje AddResenje(ResenjePostDTO r)
        {
            try
            {
                ISession s = DataLayer.GetSession();
                Resenje resenje = new Resenje();
                resenje.Datum = DateTime.Now;
                resenje.ImageInBytes = r.ImageInBytes;
                resenje.ThumbnailInBytes = r.ThumbnailInBytes;
                resenje.Dodao = s.Query<Korisnik>().Select(q => q).Where(g => g.IdKorisnik == r.Dodao).FirstOrDefault();
                resenje.Blanket = s.Query<Blanket>().Select(x => x).Where(f => f.IdBlanket == r.Blanket).FirstOrDefault();

                s.Save(resenje);
                s.Flush();
                Resenje res = s.Query<Resenje>().Where(c => c.Dodao.IdKorisnik == r.Dodao).Where(c => c.Blanket.IdBlanket == r.Blanket).Where(c => c.Datum == resenje.Datum).FirstOrDefault();
                s.Close();
                return res;
            }
            catch (Exception ex)
            {
                return null;
            }
        }

        public Resenje RemoveResenje(int id)
        {
            try
            {
                ISession s = DataLayer.GetSession();
                Resenje r = s.Load<Resenje>(id);
                s.Delete(r);
                s.Flush();
                s.Close();
                return GetResenje(id);
            }
            catch (Exception ex)
            {
                return new Resenje();
            }
        }

        public Resenje UpdateResenje(int id, Resenje p)
        {
            try
            {
                ISession s = DataLayer.GetSession();
                Resenje r = s.Load<Resenje>(id);
                r.Blanket = p.Blanket;
                r.Datum = p.Datum;
                r.Dodao = p.Dodao;
                r.ImageInBytes = p.ImageInBytes;
                r.ThumbnailInBytes = p.ThumbnailInBytes;
                s.Update(r);
                s.Flush();
                s.Close();
                return GetResenje(id);
            }
            catch (Exception ex)
            {
                return null;
            }
        }
    }
}
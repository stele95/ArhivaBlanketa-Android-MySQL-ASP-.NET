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
    public class KomentarDataProvider
    {
        public IEnumerable<Komentar> GetKomentar()
        {
            ISession s = DataLayer.GetSession();

            IEnumerable<Komentar> k = s.Query<Komentar>().Select(r => r);

            return k;
        }
        public Komentar GetKomentar(int id)
        {
            ISession s = DataLayer.GetSession();

            return s.Query<Komentar>().Where(r => r.IdKomentar == id).Select(p => p).FirstOrDefault();

        }

        public IEnumerable<KomentarDTO> GetKomentarDTO(int id, bool blanket)
        {
            ISession s = DataLayer.GetSession();
            IEnumerable<Komentar> k;
            if (blanket)
            {
                k = s.Query<Komentar>().Select(r => r).Where(x => x.Blanket.IdBlanket == id);
            }
            else
            {
                k = s.Query<Komentar>().Select(r => r).Where(x => x.Resenje.IdResenje == id);
            }

            return Mapper.Map<IEnumerable<Komentar>, IEnumerable<KomentarDTO>>(k);
        }
        
        public Komentar AddKomentar(KomentarPostDTO r)
        {
            try
            {
                ISession s = DataLayer.GetSession();

                Komentar kom = new Komentar();
                kom.Datum = DateTime.Now;
                kom.Dodao= s.Query<Korisnik>().Select(x => x).Where(q => q.IdKorisnik == r.Dodao).FirstOrDefault();
                if (r.PripadaBlanketu)
                {
                    kom.Blanket = s.Query<Blanket>().Select(d => d).Where(v => v.IdBlanket == r.ID).FirstOrDefault();
                    kom.Resenje = null;
                }
                else
                {
                    kom.Resenje = s.Query<Resenje>().Select(d => d).Where(v => v.IdResenje == r.ID).FirstOrDefault();
                    kom.Blanket = null;
                }
                kom.PripadaBlanketu = r.PripadaBlanketu;

                kom.KomentarData = r.KomentarData;

                s.Save(kom);
                s.Flush();
                Komentar f;
                if (r.PripadaBlanketu)
                {
                    f = s.Query<Komentar>().Where(c => c.Dodao.IdKorisnik == r.Dodao).Where(c => c.Datum == kom.Datum)
                        .Where(c => c.KomentarData == r.KomentarData).Where(c => c.PripadaBlanketu == r.PripadaBlanketu)
                        .Where(c => c.Blanket.IdBlanket == r.ID).FirstOrDefault();
                }
                else
                {
                    f = s.Query<Komentar>().Where(c => c.Dodao.IdKorisnik == r.Dodao).Where(c => c.Datum == kom.Datum)
                    .Where(c => c.KomentarData == r.KomentarData).Where(c => c.PripadaBlanketu == r.PripadaBlanketu)
                    .Where(c => c.Resenje.IdResenje == r.ID).FirstOrDefault();
                }
                s.Close();
                return f;
            }
            catch (Exception ex)
            {
                return null;
            }
        }
        public Komentar RemoveKomentar(int id)
        {
            try
            {
                ISession s = DataLayer.GetSession();
                Komentar r = s.Load<Komentar>(id);
                s.Delete(r);
                s.Flush();
                s.Close();
                return GetKomentar(id);
            }
            catch (Exception ex)
            {
                return new Komentar();
            }
        }
        public Komentar UpdateKomentar(int id, Komentar k)
        {
            try
            {
                ISession s = DataLayer.GetSession();
                Komentar r = s.Load<Komentar>(id);
                r.KomentarData = k.KomentarData;
                r.Blanket = k.Blanket;
                r.Resenje = k.Resenje;
                r.PripadaBlanketu = k.PripadaBlanketu;
                r.Datum = k.Datum;
                r.Dodao = k.Dodao;
                s.Update(r);
                s.Flush();
                s.Close();
                return GetKomentar(id);
            }
            catch (Exception ex)
            {
                return null;
            }
        }
    }
}
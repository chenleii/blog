// @ts-ignore
import { Request, Response } from 'express';

export default {
  'POST /api/blog/account/refreshLogin': (req: Request, res: Response) => {
    res.status(200).send({});
  },
};
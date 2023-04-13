// @ts-ignore
import { Request, Response } from 'express';

export default {
  'POST /api/blog/notification/:notificationId/clear': (req: Request, res: Response) => {
    res.status(200).send({});
  },
};
